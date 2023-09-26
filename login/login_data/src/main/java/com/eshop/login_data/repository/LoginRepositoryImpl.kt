package com.eshop.login_data.repository

import com.eshop.login_data.mapper.toAccessToken
import com.eshop.login_data.remote.LoginApi
import com.eshop.login_domain.models.AccessToken
import com.eshop.login_domain.models.Credentials
import com.eshop.login_domain.repository.LoginRepository
import javax.inject.Inject
import com.eshop.core.util.Result
import com.eshop.core.util.handleLoginApiFailure

class LoginRepositoryImpl @Inject constructor(
    private val loginApi: LoginApi
): LoginRepository {

    override suspend fun login(credentials: Credentials): Result<AccessToken> {
        return try {
            Result.Success(loginApi.login(credentials).toAccessToken())
        } catch (ex: Exception) {
            println(ex)
            handleLoginApiFailure(ex)
        }
    }
}
