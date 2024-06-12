package com.eshop.core.domain.preferences

import com.eshop.core.util.UserType

interface Preferences {
    fun saveToken(token: String)
    fun readToken(): String?
    fun deleteToken()
    fun saveUserType(userType: UserType)
    fun readUserType(): UserType?

    companion object {
        const val KEY_TOKEN = "access_token"
        const val USER_TYPE = "user_type"
    }

}