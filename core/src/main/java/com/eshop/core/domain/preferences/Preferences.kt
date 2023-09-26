package com.eshop.core.domain.preferences

interface Preferences {
    fun saveToken(token: String)
    fun readToken(): String?

    companion object {
        const val KEY_TOKEN = "access_token"
    }

}