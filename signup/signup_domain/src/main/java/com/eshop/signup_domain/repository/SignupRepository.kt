package com.eshop.signup_domain.repository

import com.eshop.core.domain.models.AccessToken
import com.eshop.signup_domain.models.RegistrationData
import com.eshop.core.util.Result

interface SignupRepository {
    suspend fun register(data: RegistrationData) : Result<AccessToken>
}