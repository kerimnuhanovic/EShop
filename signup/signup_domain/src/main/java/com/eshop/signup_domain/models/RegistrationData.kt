package com.eshop.signup_domain.models

data class RegistrationData(
    val name: String,
    val surname: String,
    val username: String,
    val email: String,
    val password: String,
    val userType: String,
    val profileImage: String
)
