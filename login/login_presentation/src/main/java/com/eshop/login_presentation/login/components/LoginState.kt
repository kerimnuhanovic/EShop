package com.eshop.login_presentation.login.components

data class LoginState(
    val credentials: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val errorMessageId: Int? = null,
    val isLoading: Boolean = false
)
