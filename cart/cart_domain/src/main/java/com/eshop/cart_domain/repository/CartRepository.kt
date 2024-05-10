package com.eshop.cart_domain.repository

import com.eshop.core.domain.models.Order
import com.eshop.core.domain.models.OrderDetails
import com.eshop.core.domain.models.Product
import com.eshop.core.util.Result

interface CartRepository {
    suspend fun fetchCartItems(): Result<List<Product>>
    suspend fun createOrder(orderDetails: List<OrderDetails>): Result<Order>
}