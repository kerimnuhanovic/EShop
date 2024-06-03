package com.eshop.core.data.remote.dto

import com.squareup.moshi.Json

data class AccessTokenDto(
    @field:Json(name="accessToken")
    val token: String,
    val userType: String
)
