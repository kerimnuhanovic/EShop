package com.eshop.signup_domain.usecase

import com.eshop.core.domain.models.AccessToken
import com.eshop.signup_domain.models.RegistrationData
import com.eshop.signup_domain.repository.SignupRepository
import javax.inject.Inject
import com.eshop.core.util.Result

class RegisterUseCase @Inject constructor(
    private val signupRepository: SignupRepository
) {

    suspend operator fun invoke(data: RegistrationData): Result<AccessToken> {
        return signupRepository.register(data)
    }
}