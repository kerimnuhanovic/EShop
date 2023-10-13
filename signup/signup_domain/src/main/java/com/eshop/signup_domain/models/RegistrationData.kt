package com.eshop.signup_domain.models

import java.io.File

data class RegistrationData(
    val name: String,
    val surname: String,
    val username: String,
    val email: String,
    val password: String,
    val userType: String,
    val shopCategories: String,
    val shopLocations: String,
    val profileImage: File
)
