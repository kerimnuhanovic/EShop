package com.eshop.cart_presentation

sealed interface CartEvent {
    object OnOrderSubmit : CartEvent
    data class OnDeleteCartItem( val itemIndex: Int, val productId: String) : CartEvent
}