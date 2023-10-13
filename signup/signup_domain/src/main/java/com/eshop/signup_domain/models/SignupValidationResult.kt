package com.eshop.signup_domain.models

data class SignupValidationResult(
    val isInputValid: Boolean,
    val errorMessageId: Int?
)
