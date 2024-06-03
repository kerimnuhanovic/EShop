package com.eshop.core.data.remote

import com.eshop.core.data.remote.dto.FavouriteShopDto
import com.eshop.core.data.remote.dto.ShopDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FavouriteShopApi {
    @POST("favouriteShop/addShop/{shopId}")
    suspend fun addShop(@Path("shopId") shopId: String) : FavouriteShopDto

    @GET("favouriteShop")
    suspend fun listShops() : List<ShopDto>

    @DELETE("favouriteShop/{shopId}")
    suspend fun deleteShop(@Path("shopId") shopId: String)

    @GET("favouriteShop/isShopFavourite/{shopId}")
    suspend fun isShopFavourite(@Path("shopId") shopId: String): Boolean
}