package com.eshop.signup_presentation.signup

import android.net.Uri
import com.eshop.coreui.util.ShopAndProductCategory
import com.eshop.coreui.util.ShopLocation
import com.eshop.signup_presentation.signup.util.UserRole

data class SignupState(
    val name: String = "",
    val surname: String = "",
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val profileImage: Uri = Uri.EMPTY,
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val userRole: UserRole = UserRole.CUSTOMER,
    val isCategoryDropdownMenuExpanded: Boolean = false,
    val listOfShopCategories: List<ShopAndProductCategory> = emptyList(),
    val listOfShopLocations: List<ShopLocation> = emptyList(),
    val shopLocation: String = "",
    val isLoading: Boolean = false,
    val errorMessageId: Int? = null
)
