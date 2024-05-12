package com.eshop.cart_data.remote

import com.eshop.cart_data.remote.dto.OrderDetailsRequest
import com.eshop.core.data.remote.dto.OrderDto
import com.eshop.core.data.remote.dto.ProductDto
import com.eshop.core.domain.models.OrderDetails
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CartApi {
    @GET("cart/list")
    suspend fun fetchCartItems(): List<ProductDto>

    @POST("order/add")
    suspend fun createOrder(@Body orderDetails: OrderDetailsRequest): OrderDto

    @DELETE("cart/{productId}")
    suspend fun deleteCartItem(@Path("productId") productId: String)
}