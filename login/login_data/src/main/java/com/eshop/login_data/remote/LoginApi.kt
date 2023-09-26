package com.eshop.login_data.remote

import com.eshop.login_data.remote.dto.AccessTokenDto
import com.eshop.login_domain.models.Credentials
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("/login")
    suspend fun login(@Body credentials: Credentials): AccessTokenDto
}
