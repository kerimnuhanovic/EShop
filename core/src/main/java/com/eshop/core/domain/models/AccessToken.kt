package com.eshop.core.domain.models

import com.eshop.core.util.UserType

data class AccessToken(
    val token: String,
    val userType: UserType
)

