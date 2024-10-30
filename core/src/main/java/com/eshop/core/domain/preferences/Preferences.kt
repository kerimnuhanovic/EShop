package com.eshop.core.domain.preferences

import com.eshop.core.domain.models.AccessToken
import com.eshop.core.util.UserType

interface Preferences {
    fun saveToken(token: String)
    fun readToken(): String?
    fun deleteToken()
    fun saveUserType(userType: UserType)
    fun readUserType(): UserType?
    fun saveUserInfo(accessToken: AccessToken)
    fun readUsername(): String?
    fun readProfileImageUrl(): String?

    companion object {
        const val KEY_TOKEN = "access_token"
        const val USER_TYPE = "user_type"
        const val PROFILE_IMAGE = "profile_image"
        const val USERNAME = "username"
    }

}