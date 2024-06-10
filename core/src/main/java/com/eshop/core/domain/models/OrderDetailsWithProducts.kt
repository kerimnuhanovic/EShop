package com.eshop.core.domain.models

import com.eshop.core.util.OrderStatus

data class OrderDetailsWithProducts(
    val id: String? = null,
    val shop: String,
    val items: List<Product>,
    val status: OrderStatus = OrderStatus.Pending
)
