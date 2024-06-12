package com.eshop.core.data.remote.dto

data class OrderWithProductsDto(
    val id: String,
    val customer: String,
    val orderDetails: List<OrderDetailsWithProductsDto>,
    val dateCreated: String
)
