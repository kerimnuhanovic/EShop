package com.eshop.signup_domain.usecase

import android.net.Uri
import com.eshop.core.R
import com.eshop.signup_domain.models.SignupValidationResult
import javax.inject.Inject

class SignupInputVerificationUseCase @Inject constructor() {
    operator fun invoke(
        name: String,
        surname: String,
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
        userType: String,
        profileImage: Uri,
        shopCategories: List<String>
    ): SignupValidationResult {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@(.+)$")
        return when {
            name.length < 2 -> SignupValidationResult(false, R.string.name_too_short)
            surname.length < 2 -> SignupValidationResult(false, R.string.surname_too_short)
            username.length < 3 -> SignupValidationResult(false, R.string.username_too_short)
            !emailRegex.matches(email) -> SignupValidationResult(false, R.string.email_invalid_format)
            password.length < 8 -> SignupValidationResult(false, R.string.password_too_short)
            password != confirmPassword -> SignupValidationResult(false, R.string.passwords_dont_match)
            userType == "Shop" && shopCategories.isEmpty() -> SignupValidationResult(false, R.string.shop_categories_empty)
            profileImage == Uri.EMPTY -> SignupValidationResult(false, R.string.profile_image_missing)
            else -> SignupValidationResult(true, null)
        }
    }
}