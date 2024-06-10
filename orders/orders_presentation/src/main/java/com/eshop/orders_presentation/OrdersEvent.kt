package com.eshop.orders_presentation

sealed interface OrdersEvent {
    object OnNavigateBack : OrdersEvent
    data class OnOrderClick(val index: Int) : OrdersEvent
    data class OnAcceptClick(val id: String, val orderDetailsId: String) : OrdersEvent
    data class OnDeclineClick(val id: String, val orderDetailsId: String) : OrdersEvent
}