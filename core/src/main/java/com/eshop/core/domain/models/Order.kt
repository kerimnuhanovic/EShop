package com.eshop.core.domain.models

import java.time.LocalDate

data class Order(
    val id: String,
    val customer: String,
    val orderDetails: List<OrderDetails>,
    val dateCreated: LocalDate
)
