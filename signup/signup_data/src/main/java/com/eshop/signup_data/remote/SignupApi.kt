package com.eshop.signup_data.remote

import retrofit2.http.POST

interface SignupApi {

    @POST("/signup")
    fun registerUser()
}