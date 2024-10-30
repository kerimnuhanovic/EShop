package com.eshop.core.data.mapper

import com.eshop.core.data.remote.dto.AccessTokenDto
import com.eshop.core.domain.models.AccessToken
import com.eshop.core.util.UserType

fun AccessTokenDto.toAccessToken() = AccessToken(
    token = token,
    userType = UserType.fromString(userType),
    username = username,
    profileImage = profileImage
)
