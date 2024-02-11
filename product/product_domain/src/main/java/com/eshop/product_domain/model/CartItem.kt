package com.eshop.product_domain.model

data class CartItem(
    val id: String,
    val customerId: String,
    val productId: String
)
