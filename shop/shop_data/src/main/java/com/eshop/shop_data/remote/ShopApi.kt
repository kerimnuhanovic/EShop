package com.eshop.shop_data.remote

import com.eshop.core.data.remote.dto.ProductDto
import com.eshop.core.data.remote.dto.ShopDto
import com.eshop.shop_data.remote.dto.AllReviewsDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ShopApi {
    @GET("shop/getShop/{shopId}")
    suspend fun fetchShop(@Path("shopId") shopId: String): ShopDto

    @GET("shop/getShopProducts/{shopId}")
    suspend fun fetchShopProducts(@Path("shopId") shopId: String): List<ProductDto>

    @GET("shop/reviews/{shopId}")
    suspend fun fetchShopReviews(@Path("shopId") shopId: String): AllReviewsDto
}