package com.eshop.orders_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eshop.core.domain.models.Order
import com.eshop.core.domain.models.OrderDetailsWithProducts
import com.eshop.core.domain.models.OrderWithProducts
import com.eshop.core.domain.preferences.Preferences
import com.eshop.core.util.OrderStatus
import com.eshop.core.util.Result
import com.eshop.core.util.ToastMessage
import com.eshop.coreui.util.UiEvent
import com.eshop.coreui.util.generateBottomBarItems
import com.eshop.orders_domain.usecase.FetchOrdersUseCase
import com.eshop.orders_domain.usecase.UpdateOrderStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val preferences: Preferences,
    private val fetchOrdersUseCase: FetchOrdersUseCase,
    private val updateOrderStatusUseCase: UpdateOrderStatusUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<OrdersState> = MutableStateFlow(OrdersState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        generateBarItems()
        fetchOrders()
    }

    fun onEvent(event: OrdersEvent) {
        when (event) {
            OrdersEvent.OnNavigateBack -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigateBack)
                }
            }

            is OrdersEvent.OnOrderClick -> {
                //val newList = state.value.expandedItems.toMutableList()
                //newList[event.index] = !state.value.expandedItems[event.index]
                val newList = state.value.expandedItems.mapIndexed { index, isExpanded ->
                    if (index == event.index) {
                        !isExpanded
                    } else false
                }
                _state.value = state.value.copy(
                    expandedItems = newList
                )
            }

            is OrdersEvent.OnAcceptClick -> {
                updateOrderStatus(event.id, event.orderDetailsId, OrderStatus.Approved)
            }
            is OrdersEvent.OnDeclineClick -> {
                updateOrderStatus(event.id, event.orderDetailsId, OrderStatus.Declined)
            }
        }
    }

    private fun generateBarItems() {
        viewModelScope.launch {
            val userType = preferences.readUserType()!!
            _state.value = _state.value.copy(
                bottomBarItems = generateBottomBarItems(userType.type),
                userType = userType
            )
        }
    }

    private fun fetchOrders() {
        _state.value = state.value.copy(
            isLoading = true
        )
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = fetchOrdersUseCase(preferences.readUserType()!!)) {
                is Result.Success -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        orders = result.data,
                        expandedItems = List(result.data.sumOf { it.orderDetails.size }) { false }
                    )
                }
                is Result.Failure -> TODO()
            }
        }
    }

    private fun updateOrderStatus(id: String, orderDetailsId: String, status: OrderStatus) {
        val order = state.value.orders.find {
            it.id == id
        }
        val orderDetails = order!!.orderDetails.find {
            it.id == orderDetailsId
        }!!
        val oldOrdersState = state.value.orders
        val modifierOrder = OrderWithProducts(
            id = order.id,
            customer = order.customer,
            dateCreated = order.dateCreated,
            orderDetails = order.orderDetails.map {
                if (it.id != orderDetails.id) {
                    it
                } else OrderDetailsWithProducts(
                    id = orderDetails.id,
                    shop = orderDetails.shop,
                    status = status,
                    items = orderDetails.items
                )
            }
        )
        _state.value = state.value.copy(
            orders = state.value.orders.map {
                if (it.id == order.id) {
                    modifierOrder
                } else it
            },
            isAcceptStatusSubmitting = status is OrderStatus.Approved,
            isDeclineStatusSubmitting = status is OrderStatus.Declined
        )
        viewModelScope.launch(Dispatchers.IO) {
            when (updateOrderStatusUseCase(id, orderDetailsId, status)) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        isDeclineStatusSubmitting = false,
                        isAcceptStatusSubmitting = false
                    )
                    val toastMessage = if (status is OrderStatus.Approved) ToastMessage.OrderApproved else ToastMessage.OrderDeclined
                    _uiEvent.send(UiEvent.DisplayToast(toastMessage.message))
                }
                is Result.Failure -> {
                    _uiEvent.send(UiEvent.DisplayToast(ToastMessage.ChangeOrderStatusFailed.message))
                    _state.value = state.value.copy(
                        orders = oldOrdersState
                    )
                }
            }
        }
    }
}