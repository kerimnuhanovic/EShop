package com.eshop.login_domain.repository

import com.eshop.core.domain.models.AccessToken
import com.eshop.login_domain.models.Credentials
import com.eshop.core.util.Result

interface LoginRepository {

    suspend fun login(credentials: Credentials): Result<AccessToken>
}

