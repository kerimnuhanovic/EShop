package com.eshop.login_domain.usecase

import com.eshop.login_domain.models.Credentials
import com.eshop.login_domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(credentials: Credentials) = loginRepository.login(credentials)

}