package com.eshop.core.data.remote.dto

data class OrderDto(
    val id: String,
    val customer: String,
    val orderDetails: List<OrderDetailsDto>,
    val dateCreated: String
)
