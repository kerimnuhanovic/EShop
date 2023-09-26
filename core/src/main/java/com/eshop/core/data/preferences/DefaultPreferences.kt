package com.eshop.core.data.preferences

import android.content.SharedPreferences
import com.eshop.core.domain.preferences.Preferences
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
}