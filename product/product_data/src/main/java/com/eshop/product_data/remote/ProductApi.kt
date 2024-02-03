package com.eshop.product_data.remote

import com.eshop.core.data.remote.dto.ProductDto
import com.eshop.core.data.remote.dto.ShopDto
import com.eshop.product_data.remote.dto.CartItemDto
import com.eshop.product_data.remote.request.AddProductToCartRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductApi {
    @GET("product/getProduct/{productId}")
    suspend fun fetchProduct(@Path("productId") productId: String): ProductDto
    @GET("shop/getShop/{shopId}")
    suspend fun fetchShop(@Path("shopId") shopId: String): ShopDto
    @POST("cart/addProductToCart")
    suspend fun addProductToCart(@Body productIdRequest: AddProductToCartRequest): CartItemDto
}