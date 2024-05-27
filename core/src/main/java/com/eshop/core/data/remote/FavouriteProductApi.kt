package com.eshop.core.data.remote

import com.eshop.core.data.remote.dto.FavouriteProductDto
import com.eshop.core.data.remote.dto.ProductDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FavouriteProductApi {
    @POST("favouriteProduct/addProduct/{productId}")
    suspend fun addProduct(@Path("productId") productId: String) : FavouriteProductDto

    @GET("favouriteProduct")
    suspend fun listProducts() : List<ProductDto>

    @DELETE("favouriteProduct/{productId}")
    suspend fun deleteProduct(@Path("productId") productId: String)

    @GET("favouriteProduct/isProductFavourite/{productId}")
    suspend fun isProductFavourite(@Path("productId") productId: String): Boolean
}