package com.eshop.core.data.remote.dto

data class OrderDetailsWithProductsDto(
    val id: String,
    val shop: String,
    val items: List<ProductDto>,
    val status: String
)
