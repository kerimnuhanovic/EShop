package com.eshop.core.domain.models

import com.eshop.core.util.OrderStatus

data class OrderDetails(
    val id: String? = null,
    val shop: String,
    val items: List<String>,
    val status: OrderStatus = OrderStatus.Pending
)
