package com.eshop.core.domain.models

import java.time.LocalDate

data class OrderWithProducts(
    val id: String,
    val customer: String,
    val orderDetails: List<OrderDetailsWithProducts>,
    val dateCreated: LocalDate
)
