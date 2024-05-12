package com.eshop.cart_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eshop.cart_domain.usecase.CreateOrderUseCase
import com.eshop.cart_domain.usecase.FetchCartItemsUseCase
import com.eshop.core.domain.models.OrderDetails
import com.eshop.core.util.DELAY_1000
import com.eshop.core.util.Result
import com.eshop.core.util.ToastMessage
import com.eshop.coreui.navigation.Route
import com.eshop.coreui.util.UiEvent
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
class CartViewModel @Inject  constructor(
    private val fetchCartItemsUseCase: FetchCartItemsUseCase,
    private val createOrderUseCase: CreateOrderUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<CartState> = MutableStateFlow(CartState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        fetchCartItems()
    }

    fun onEvent(event: CartEvent) {
        when (event) {
            CartEvent.OnOrderSubmit -> {
                createOrder()
            }
        }
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
                        } + if (result.data.isEmpty()) 0.0 else state.value.deliveryCharge,
                        deliveryCharge = if (result.data.isEmpty()) 0.0 else state.value.deliveryCharge
                    )
                }
                is Result.Failure -> {

                }
            }
        }
    }

    private fun createOrder() {
        _state.value = state.value.copy(
            isOrderSubmitting = true
        )
        viewModelScope.launch(Dispatchers.IO) {
            val orderDetailsMap = state.value.products.groupBy {
                it.shop
            }
            val orderDetails = orderDetailsMap.map {
                OrderDetails(
                    shop = it.key,
                    items = it.value.map { product ->
                        product.id
                    }
                )
            }
            println(orderDetails)
            when (createOrderUseCase(orderDetails)) {
                is Result.Success -> {
                    _state.value = state.value.copy(
                        isOrderSubmitting = false
                    )
                    _uiEvent.send(UiEvent.DisplayToast(ToastMessage.OrderSubmitted.message))
                    delay(DELAY_1000)
                    _uiEvent.send(UiEvent.Navigate(Route.PRODUCTS_OVERVIEW))
                }
                is Result.Failure -> {

                }
            }
        }
    }

}