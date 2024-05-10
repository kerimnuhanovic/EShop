package com.eshop.cart_data.remote

import com.eshop.core.data.remote.dto.ProductDto
import retrofit2.http.GET

interface CartApi {
    @GET("cart/list")
    suspend fun fetchCartItems(): List<ProductDto>
}