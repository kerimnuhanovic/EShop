package com.eshop.core.data.mapper

import com.eshop.core.data.remote.dto.AccessTokenDto
import com.eshop.core.domain.models.AccessToken

fun AccessTokenDto.toAccessToken() = AccessToken(token)
