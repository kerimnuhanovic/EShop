package com.eshop.product_presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eshop.core.util.Result
import com.eshop.coreui.util.UiEvent
import com.eshop.product_domain.usecase.AddProductToCartUseCase
import com.eshop.product_domain.usecase.FetchProductUseCase
import com.eshop.product_domain.usecase.FetchShopUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val addProductToCartUseCase: AddProductToCartUseCase
): ViewModel() {
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
        }
    }

    private fun addProductToCart(productId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isAddingProductInProgress = true
            )
            when (val result = addProductToCartUseCase(productId)) {
                is Result.Success -> {
                    // save result to local db
                    // display alert that product is added
                    println(result.data)
                    println("EVO ME U SUCCESS")
                    _state.value = _state.value.copy(
                        isAddingProductInProgress = false
                    )
                }
                is Result.Failure -> {
                    // display error message
                    println("EVO ME U FAILURE")
                    _state.value = _state.value.copy(
                        isAddingProductInProgress = false
                    )
                }
            }
        }
    }

    private fun fetchProduct() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true
            )
            delay(6000L)
            when (val result = fetchProductUseCase(productId)) {
                is Result.Success -> {
                    when (val productOwner = fetchProductOwnerUseCase(result.data.shop)) {
                        is Result.Success -> {
                            _state.value = _state.value.copy(
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
    }
}