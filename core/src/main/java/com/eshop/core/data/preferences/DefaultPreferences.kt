package com.eshop.core.data.preferences

import android.content.SharedPreferences
import com.eshop.core.domain.preferences.Preferences
import com.eshop.core.util.UserType
import javax.inject.Inject

class DefaultPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
): Preferences {
    override fun saveToken(token: String) {
        sharedPreferences.edit().putString(Preferences.KEY_TOKEN, "Bearer $token").apply()
    }

    override fun readToken(): String? {
        return sharedPreferences.getString(Preferences.KEY_TOKEN, null)
    }

    override fun deleteToken() {
        sharedPreferences.edit().putString(Preferences.KEY_TOKEN, null).apply()
    }

    override fun saveUserType(userType: UserType) {
        sharedPreferences.edit().putString(Preferences.USER_TYPE, userType.type).apply()
    }

    override fun readUserType(): UserType? {
        val type = sharedPreferences.getString(Preferences.USER_TYPE, null)
        return if (type != null) {
            UserType.fromString(type)
        } else null
    }
}