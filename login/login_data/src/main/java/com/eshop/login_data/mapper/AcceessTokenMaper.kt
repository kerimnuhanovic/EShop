package com.eshop.login_data.mapper

import com.eshop.login_data.remote.dto.AccessTokenDto
import com.eshop.login_domain.models.AccessToken

fun AccessTokenDto.toAccessToken() = AccessToken(token)
