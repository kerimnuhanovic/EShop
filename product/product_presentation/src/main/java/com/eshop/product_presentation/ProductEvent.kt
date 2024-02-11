package com.eshop.product_presentation

sealed interface ProductEvent {
    object OnBackClick : ProductEvent
    data class OnAddToCartClick(val productId: String) : ProductEvent
}