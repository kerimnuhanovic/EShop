package com.eshop.login_data.remote.dto

import com.squareup.moshi.Json

data class AccessTokenDto(
    @field:Json(name="accessToken")
    val token: String
)
