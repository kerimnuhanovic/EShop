package com.eshop.orders_domain.repository

import com.eshop.core.domain.models.OrderWithProducts
import com.eshop.core.util.OrderStatus
import com.eshop.core.util.Result
import com.eshop.core.util.UserType

interface OrdersRepository {
    suspend fun fetchOrders(userType: UserType): Result<List<OrderWithProducts>>
    suspend fun updateOrderStatus(id: String, orderDetailsId: String, status: OrderStatus): Result<Unit>
}