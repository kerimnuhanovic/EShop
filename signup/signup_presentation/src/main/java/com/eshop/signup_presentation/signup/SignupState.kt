package com.eshop.signup_presentation.signup

import android.net.Uri

data class SignupState(
    val name: String = "",
    val surname: String = "",
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val userType: String = "",
    val profileImage: Uri = Uri.EMPTY,
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false
)
