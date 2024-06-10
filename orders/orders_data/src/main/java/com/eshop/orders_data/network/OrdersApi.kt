package com.eshop.orders_data.network

import com.eshop.core.data.remote.dto.OrderWithProductsDto
import com.eshop.orders_data.network.dto.UpdateOrderStatusBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface OrdersApi {
    @GET("order/customer-orders")
    suspend fun fetchCustomerOrders(): List<OrderWithProductsDto>

    @GET("order/shop-orders")
    suspend fun fetchShopOrders(): List<OrderWithProductsDto>

    @PATCH("order/status")
    suspend fun updateOrderStatus(@Body body: UpdateOrderStatusBody)
}