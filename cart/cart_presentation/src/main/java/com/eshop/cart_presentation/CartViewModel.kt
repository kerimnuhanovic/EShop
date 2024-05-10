package com.eshop.cart_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eshop.cart_domain.usecase.FetchCartItemsUseCase
import com.eshop.core.util.Result
import com.eshop.coreui.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject  constructor(
    private val fetchCartItemsUseCase: FetchCartItemsUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<CartState> = MutableStateFlow(CartState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        fetchCartItems()
    }

    fun onEvent(event: CartEvent) {

    }

    private fun fetchCartItems() {
        _state.value = state.value.copy(
            isLoading = true
        )
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = fetchCartItemsUseCase()) {
                is Result.Success -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        products = result.data,
                        subTotal = result.data.sumOf { product ->
                            product.price
                        },
                        total = result.data.sumOf { product ->
                            product.price
                        } + state.value.deliveryCharge
                    )
                }
                is Result.Failure -> {

                }
            }
        }
    }

}