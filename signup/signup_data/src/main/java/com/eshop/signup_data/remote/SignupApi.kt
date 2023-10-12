package com.eshop.signup_data.remote

import com.eshop.core.data.remote.dto.AccessTokenDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SignupApi {

    @Multipart
    @POST("/signup")
    suspend fun registerUser(
        @Part("name") name: RequestBody,
        @Part("surname") surname: RequestBody,
        @Part("username") username: RequestBody,
        @Part("email") email: RequestBody,
        @Part("userType") userType: RequestBody,
        @Part("password") password: RequestBody,
        @Part("shopCategories") shopCategories: RequestBody,
        @Part("shopLocations") shopLocations: RequestBody,
        @Part profileImage: MultipartBody.Part
    ) : AccessTokenDto
}