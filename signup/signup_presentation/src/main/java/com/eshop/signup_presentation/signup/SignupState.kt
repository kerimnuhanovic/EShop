package com.eshop.signup_presentation.signup

import android.net.Uri
import com.eshop.signup_presentation.signup.util.ShopCategory
import com.eshop.signup_presentation.signup.util.ShopLocation
import com.eshop.signup_presentation.signup.util.UserRole

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
    val isConfirmPasswordVisible: Boolean = false,
    val userRole: UserRole = UserRole.CUSTOMER,
    val isCategoryDropdownMenuExpanded: Boolean = false,
    val listOfShopCategories: List<ShopCategory> = emptyList(),
    val listOfShopLocations: List<ShopLocation> = emptyList(),
    val shopLocation: String = ""
)
