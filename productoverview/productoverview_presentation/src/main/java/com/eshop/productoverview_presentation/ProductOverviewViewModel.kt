package com.eshop.productoverview_presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eshop.core.domain.usecase.ConvertListToStringUseCase
import com.eshop.core.domain.usecase.CreateFileFromUriUseCase
import com.eshop.core.navigation.Route
import com.eshop.core.util.Result
import com.eshop.coreui.util.ShopAndProductCategory
import com.eshop.coreui.util.UiEvent
import com.eshop.productoverview_domain.model.ProductAdditionData
import com.eshop.productoverview_domain.usecase.AddProductInputValidationUseCase
import com.eshop.productoverview_domain.usecase.AddProductUseCase
import com.eshop.productoverview_domain.usecase.FetchAllProductsUseCase
import com.eshop.productoverview_domain.usecase.FetchPopularProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductOverviewViewModel @Inject constructor(
    private val addProductUseCase: AddProductUseCase,
    private val createFileFromUriUseCase: CreateFileFromUriUseCase,
    private val convertListToStringUseCase: ConvertListToStringUseCase,
    private val addProductInputValidationUseCase: AddProductInputValidationUseCase,
    private val fetchPopularProductsUseCase: FetchPopularProductsUseCase,
    private val fetchAllProductsUseCase: FetchAllProductsUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<ProductOverviewState> =
        MutableStateFlow(ProductOverviewState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        fetchInitialProducts()
    }

    fun onEvent(event: ProductOverviewEvent) {
        when (event) {
            ProductOverviewEvent.OnSearchIconClick -> {
                _state.value = _state.value.copy(
                    isSearchBarVisible = true
                )
            }

            is ProductOverviewEvent.OnSearchQueryEnter -> {
                _state.value = _state.value.copy(
                    searchQuery = event.query
                )
            }

            ProductOverviewEvent.OnAddProductClick -> {
                addProduct()
            }

            is ProductOverviewEvent.OnProductDescriptionEnter -> {
                _state.value = _state.value.copy(
                    productDescription = event.description
                )
            }

            is ProductOverviewEvent.OnProductPriceEnter -> {
                _state.value = _state.value.copy(
                    productPrice = event.price
                )
            }

            is ProductOverviewEvent.OnProductTitleEnter -> {
                _state.value = _state.value.copy(
                    productTitle = event.title
                )
            }

            ProductOverviewEvent.OnExpandChange -> {
                _state.value = _state.value.copy(
                    isCategoryDropdownMenuExpanded = !state.value.isCategoryDropdownMenuExpanded
                )
            }

            is ProductOverviewEvent.OnProductCategoryClick -> {
                if (!_state.value.listOfProductCategories.contains(event.productCategory)) {
                    _state.value = _state.value.copy(
                        listOfProductCategories = _state.value.listOfProductCategories.plus(event.productCategory)
                    )
                } else {
                    _state.value = _state.value.copy(
                        listOfProductCategories = _state.value.listOfProductCategories.filterNot {
                            it == event.productCategory
                        }
                    )
                }
            }

            is ProductOverviewEvent.OnProductPhotosSelect -> {
                val newImages = event.photos.filter { newImage ->
                    !state.value.productImages.contains(newImage)
                }
                _state.value = _state.value.copy(
                    productImages = _state.value.productImages + newImages
                )
                println(newImages)
            }

            is ProductOverviewEvent.OnProductPhotoRemove -> {
                _state.value = _state.value.copy(
                    productImages = _state.value.productImages.filterNot {
                        it === event.photo
                    }
                )
            }

            ProductOverviewEvent.OnScreenEndReach -> {
                loadMoreProducts()
            }

            is ProductOverviewEvent.OnProductClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate("${Route.PRODUCT}/${event.productId}"))
                }
            }

            ProductOverviewEvent.OnDeleteSearchTextClick -> {
                _state.value = _state.value.copy(
                    searchQuery = ""
                )
            }
            ProductOverviewEvent.OnExitSearchBarClick -> {
                _state.value = _state.value.copy(
                    searchQuery = "",
                    isSearchBarVisible = false
                )
            }
            ProductOverviewEvent.OnFilterIconClick -> {
                // TODO
            }
            ProductOverviewEvent.OnSearch -> {
                onSearch()
            }
        }
    }

    private suspend fun fetchPopularProducts() {
        when (val result = fetchPopularProductsUseCase()) {
            is Result.Success -> {
                _state.value = _state.value.copy(
                    isPopularProductsLoading = false,
                    popularProducts = result.data
                )
            }
            is Result.Failure -> {
                // TODO
            }
        }
    }

    private suspend fun fetchAllProducts(offset: Int, searchQuery: String? = null) {
        when (val result = fetchAllProductsUseCase(offset, searchQuery)) {
            is Result.Success -> {
                _state.value = _state.value.copy(
                    isAllProductsLoading = false,
                    allProducts = state.value.allProducts + result.data,
                    areAllProductsLoaded = result.data.isEmpty()
                )
            }
            is Result.Failure -> {
                // TODO
            }
        }
    }

    private fun fetchInitialProducts() {
        _state.value = _state.value.copy(
            isPopularProductsLoading = true,
            isAllProductsLoading = true
        )
        viewModelScope.launch(Dispatchers.IO) {
            launch {
                fetchPopularProducts()
            }
            launch {
                fetchAllProducts(state.value.allProducts.size)
            }
        }
    }

    private fun loadMoreProducts() {
        if (state.value.areAllProductsLoaded || state.value.isLoadingMoreProducts) return
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoadingMoreProducts = true
            )
            fetchAllProducts(offset = state.value.allProducts.size, searchQuery = state.value.searchQuery)
            _state.value = _state.value.copy(
                isLoadingMoreProducts = false
            )
        }
    }

    private fun fetchSearchedProducts() {
        _state.value = state.value.copy(
            isAllProductsLoading = true,
            allProducts = emptyList(),
            isSearchBarVisible = false,
            searchedQuery = state.value.searchQuery
        )
        viewModelScope.launch {
            fetchAllProducts(state.value.allProducts.size, searchQuery = state.value.searchQuery)
        }
    }
    private fun onSearch() {
        if (state.value.searchQuery.isEmpty()) {
            fetchInitialProducts()
        }
        else {
            fetchSearchedProducts()
        }
    }

    private fun addProduct() {
        viewModelScope.launch {
            val validationResult = addProductInputValidationUseCase(
                title = state.value.productTitle,
                description = state.value.productDescription,
                price = state.value.productPrice,
                categories = state.value.listOfProductCategories.map { it.value },
                images = state.value.productImages
            )
            if (!validationResult.isInputValid) {
                _state.value = _state.value.copy(
                    errorMessageId = validationResult.errorMessageId
                )
                return@launch
            }
            _state.value = _state.value.copy(
                isProductAdditionInProgress = true
            )
            val result = addProductUseCase(
                ProductAdditionData(
                    title = state.value.productTitle,
                    description = state.value.productDescription,
                    categories = convertListToStringUseCase(state.value.listOfProductCategories.map { it.value }),
                    price = state.value.productPrice.toDouble(),
                    images = state.value.productImages.map {
                        createFileFromUriUseCase(it)
                    }
                )
            )
            when (result) {
                is Result.Success -> {
                    _state.value =
                        ProductOverviewState(popularProducts = state.value.popularProducts)
                }

                is Result.Failure -> {
                    _state.value = _state.value.copy(
                        errorMessageId = result.errorMessageId
                    )
                }
            }
            _state.value = _state.value.copy(
                isProductAdditionInProgress = false
            )
        }
    }

}