package com.eshop.productoverview_data.remote

import com.eshop.productoverview_data.remote.dto.ProductDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ProductApi {

    @Multipart
    @POST("/addProduct")
    suspend fun addProduct(
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("category") category: RequestBody,
        @Part("price") price: RequestBody,
        @Part images: List<MultipartBody.Part>
    ): ProductDto
}