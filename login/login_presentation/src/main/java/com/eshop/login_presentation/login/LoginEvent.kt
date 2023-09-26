package com.eshop.login_presentation.login

sealed interface LoginEvent {
    data class OnIdentifierEnter(val identifier: String) : LoginEvent
    data class OnPasswordEnter(val password: String) : LoginEvent
    object OnPasswordVisibilityIconClick : LoginEvent
    object OnLoginClick : LoginEvent
}