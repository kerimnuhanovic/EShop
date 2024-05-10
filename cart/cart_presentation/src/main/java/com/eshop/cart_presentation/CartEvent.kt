package com.eshop.cart_presentation

sealed interface CartEvent {
    object OnOrderSubmit : CartEvent
}