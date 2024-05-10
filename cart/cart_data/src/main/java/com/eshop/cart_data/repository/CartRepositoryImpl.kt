package com.eshop.cart_data.repository

import com.eshop.cart_data.remote.CartApi
import com.eshop.cart_data.remote.dto.OrderDetailsRequest
import com.eshop.cart_domain.repository.CartRepository
import com.eshop.core.data.mapper.toOrder
import com.eshop.core.data.mapper.toOrderDetailsDto
import com.eshop.core.data.mapper.toProduct
import com.eshop.core.domain.models.Order
import com.eshop.core.domain.models.OrderDetails
import com.eshop.core.domain.models.Product
import com.eshop.core.util.Result
import com.eshop.core.util.handleApiError
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartApi: CartApi
): CartRepository{
    override suspend fun fetchCartItems(): Result<List<Product>> {
        return try {
            val cartItems = cartApi.fetchCartItems()
            val products = cartItems.map { item ->
                item.toProduct()
            }
            Result.Success(products)
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }

    override suspend fun createOrder(orderDetails: List<OrderDetails>): Result<Order> {
        return try {
            val order = cartApi.createOrder(OrderDetailsRequest(orderDetails.map { it.toOrderDetailsDto() }))
            Result.Success(order.toOrder())
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }
}