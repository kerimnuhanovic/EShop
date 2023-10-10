package com.eshop.signup_presentation.signup

import android.net.Uri
import com.eshop.signup_presentation.signup.util.ShopCategory
import com.eshop.signup_presentation.signup.util.ShopLocation
import com.eshop.signup_presentation.signup.util.UserRole

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
    data class OnUserRoleSelect(val userRole: UserRole) : SignupEvent
    object OnExpandChange : SignupEvent
    data class OnShopCategoryClick(val shopCategory: ShopCategory) : SignupEvent
    data class OnShopLocationAdd(val shopLocation: ShopLocation) : SignupEvent
    data class OnShopLocationRemove(val shopLocation: ShopLocation) : SignupEvent
    data class OnShopLocationEnter(val location: String) : SignupEvent
}