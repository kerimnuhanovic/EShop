package com.eshop.shopoverview_data.remote

import com.eshop.core.data.remote.dto.ShopDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ShopOverviewApi {
    @GET("shop/allShops/{offset}")
    suspend fun fetchAllShops(@Path("offset") offset: Int): List<ShopDto>
    @GET("shop/popularShops")
    suspend fun fetchPopularShops(): List<ShopDto>
}