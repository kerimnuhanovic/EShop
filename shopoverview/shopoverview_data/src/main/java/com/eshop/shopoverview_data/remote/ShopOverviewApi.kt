package com.eshop.shopoverview_data.remote

import com.eshop.core.data.remote.dto.ShopDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ShopOverviewApi {
    @GET("shop/allShops/{offset}")
    suspend fun fetchAllShops(@Path("offset") offset: Int, @Query("searchQuery") searchQuery: String?): List<ShopDto>
    @GET("shop/popularShops")
    suspend fun fetchPopularShops(): List<ShopDto>
}