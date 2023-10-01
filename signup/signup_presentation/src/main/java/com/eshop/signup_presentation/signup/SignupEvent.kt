package com.eshop.signup_presentation.signup

import android.net.Uri

sealed interface SignupEvent {
    data class OnNameEnter(val name: String) : SignupEvent
    data class OnSurnameEnter(val surname: String) : SignupEvent
    data class OnUsernameEnter(val username: String) : SignupEvent
    data class OnEmailEnter(val email: String) : SignupEvent
    data class OnPasswordEnter(val password: String) : SignupEvent
    data class OnConfirmPasswordEnter(val confirmPassword: String) : SignupEvent
    data class OnUserTypeSelect(val userType: String) : SignupEvent
    object OnPasswordVisibilityIconClick : SignupEvent
    object OnConfirmPasswordVisibilityIconClick : SignupEvent
    data class OnImageSelect(val image: Uri) : SignupEvent
}