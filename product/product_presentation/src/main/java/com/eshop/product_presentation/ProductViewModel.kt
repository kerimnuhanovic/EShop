package com.eshop.product_presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eshop.core.domain.usecase.AddFavouriteProductUseCase
import com.eshop.core.domain.usecase.CheckIsProductFavouriteUseCase
import com.eshop.core.domain.usecase.DeleteFavouriteProductUseCase
import com.eshop.core.util.Result
import com.eshop.core.util.ToastMessage
import com.eshop.coreui.util.UiEvent
import com.eshop.product_domain.usecase.AddProductToCartUseCase
import com.eshop.product_domain.usecase.FetchProductUseCase
import com.eshop.product_domain.usecase.FetchShopUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchProductUseCase: FetchProductUseCase,
    private val fetchProductOwnerUseCase: FetchShopUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val checkIsProductFavouriteUseCase: CheckIsProductFavouriteUseCase,
    private val addFavouriteProductUseCase: AddFavouriteProductUseCase,
    private val deleteFavouriteProductUseCase: DeleteFavouriteProductUseCase
) : ViewModel() {
    private val productId: String = checkNotNull(savedStateHandle["productId"])

    private val _state: MutableStateFlow<ProductState> = MutableStateFlow(ProductState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        fetchProduct()
    }

    fun onEvent(event: ProductEvent) {
        when (event) {
            ProductEvent.OnBackClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigateBack)
                }
            }

            is ProductEvent.OnAddToCartClick -> {
                addProductToCart(event.productId)
            }

            ProductEvent.OnFavouriteClick -> {
                changeFavouriteStatus()
            }
        }
    }

    private fun changeFavouriteStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            val isProductFavourite = state.value.isProductFavourite
            _state.value = state.value.copy(
                isProductFavourite = !state.value.isProductFavourite
            )
            if (isProductFavourite) {
                when (deleteFavouriteProductUseCase(productId)) {
                    is Result.Success -> {
                        _uiEvent.send(UiEvent.DisplayToast(ToastMessage.FavouriteProductDelete.message))
                    }

                    is Result.Failure -> {
                        _uiEvent.send(UiEvent.DisplayToast(ToastMessage.FavouriteProductDeleteFailed.message))
                        _state.value = state.value.copy(
                            isProductFavourite = !state.value.isProductFavourite
                        )
                    }
                }
            } else {
                when (addFavouriteProductUseCase(productId)) {
                    is Result.Success -> {
                        _uiEvent.send(UiEvent.DisplayToast(ToastMessage.FavouriteProductAdded.message))
                    }

                    is Result.Failure -> {
                        _uiEvent.send(UiEvent.DisplayToast(ToastMessage.FavouriteProductAddFailed.message))
                        _state.value = state.value.copy(
                            isProductFavourite = !state.value.isProductFavourite
                        )
                    }
                }
            }
        }
    }

    private fun addProductToCart(productId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isAddingProductInProgress = true
            )
            when (val result = addProductToCartUseCase(productId)) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        isAddingProductInProgress = false
                    )
                }

                is Result.Failure -> {
                    _state.value = _state.value.copy(
                        isAddingProductInProgress = false
                    )
                }
            }
        }
    }

    private fun fetchProduct() {
        viewModelScope.launch(Dispatchers.IO) {
            val productResult = viewModelScope.async {
                _state.value = _state.value.copy(
                    isLoading = true
                )
                when (val result = fetchProductUseCase(productId)) {
                    is Result.Success -> {
                        when (val productOwner = fetchProductOwnerUseCase(result.data.shop)) {
                            is Result.Success -> {
                                return@async _state.value.copy(
                                    isLoading = false,
                                    product = result.data,
                                    productOwner = productOwner.data
                                )
                            }

                            is Result.Failure -> TODO()
                        }
                    }

                    is Result.Failure -> TODO()
                }
            }
            val isProductFavouriteResult = viewModelScope.async {
                when (val result = checkIsProductFavouriteUseCase(productId)) {
                    is Result.Success -> {
                        return@async result.data
                    }

                    is Result.Failure -> TODO()
                }
            }
            val newState = productResult.await()
            val isProductFavourite = isProductFavouriteResult.await()
            _state.value = newState.copy(
                isProductFavourite = isProductFavourite
            )

        }
    }
}