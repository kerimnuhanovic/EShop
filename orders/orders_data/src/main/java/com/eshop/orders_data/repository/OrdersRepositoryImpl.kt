package com.eshop.orders_data.repository

import com.eshop.core.data.mapper.toOrderWithProducts
import com.eshop.core.domain.models.OrderWithProducts
import com.eshop.core.util.OrderStatus
import com.eshop.core.util.Result
import com.eshop.core.util.UserType
import com.eshop.core.util.handleApiError
import com.eshop.orders_data.network.OrdersApi
import com.eshop.orders_data.network.dto.UpdateOrderStatusBody
import com.eshop.orders_domain.repository.OrdersRepository
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor(
    private val ordersApi: OrdersApi
) : OrdersRepository {
    override suspend fun fetchOrders(userType: UserType): Result<List<OrderWithProducts>> {
        return try {
            when (userType) {
                UserType.Customer -> {
                    val ordersDto = ordersApi.fetchCustomerOrders()
                    val orders = ordersDto.map {
                        it.toOrderWithProducts()
                    }
                    Result.Success(orders)
                }
                UserType.Shop -> {
                    val ordersDto = ordersApi.fetchShopOrders()
                    val orders = ordersDto.map {
                        it.toOrderWithProducts()
                    }
                    Result.Success(orders)
                }
            }
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }

    override suspend fun updateOrderStatus(
        id: String,
        orderDetailsId: String,
        status: OrderStatus
    ): Result<Unit> {
        return try {
            ordersApi.updateOrderStatus(UpdateOrderStatusBody(
                id = id,
                orderDetailsId = orderDetailsId,
                status = status.name
            ))
            Result.Success(Unit)
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }
}