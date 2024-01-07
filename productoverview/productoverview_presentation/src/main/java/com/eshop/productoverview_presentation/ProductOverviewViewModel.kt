package com.eshop.productoverview_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eshop.core.domain.usecase.ConvertListToStringUseCase
import com.eshop.core.domain.usecase.CreateFileFromUriUseCase
import com.eshop.core.util.Result
import com.eshop.coreui.util.UiEvent
import com.eshop.productoverview_domain.model.ProductAdditionData
import com.eshop.productoverview_domain.usecase.AddProductInputValidationUseCase
import com.eshop.productoverview_domain.usecase.AddProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
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
    private val addProductInputValidationUseCase: AddProductInputValidationUseCase
): ViewModel() {

    private val _state: MutableStateFlow<ProductOverviewState> = MutableStateFlow(ProductOverviewState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: ProductOverviewEvent) {
        when (event) {
            ProductOverviewEvent.OnSearchIconClick -> {
                _state.value = _state.value.copy(
                    isSearchBarExpanded = !state.value.isSearchBarExpanded
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
            }

            is ProductOverviewEvent.OnProductPhotoRemove -> {
                _state.value = _state.value.copy(
                    productImages = _state.value.productImages.filterNot {
                        it === event.photo
                    }
                )
            }
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
                    _state.value = ProductOverviewState()
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