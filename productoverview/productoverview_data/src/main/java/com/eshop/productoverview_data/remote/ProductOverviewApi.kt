package com.eshop.productoverview_data.remote

import com.eshop.core.data.remote.dto.ProductDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductOverviewApi {

    @Multipart
    @POST("/product/addProduct")
    suspend fun addProduct(
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("category") category: RequestBody,
        @Part("price") price: RequestBody,
        @Part images: List<MultipartBody.Part>
    ): ProductDto

    @GET("product/popularProducts")
    suspend fun fetchPopularProduct(): List<ProductDto>

    @GET("product/allProducts/{offset}")
    suspend fun fetchAllProducts(
        @Path("offset") offset: Int,
        @Query("searchQuery") searchQuery: String?,
        @Query("filters") filters: String?,
        @Query("sortBy") sortBy: String?,
        @Query("orderBy") orderBy: String?
    ): List<ProductDto>
}