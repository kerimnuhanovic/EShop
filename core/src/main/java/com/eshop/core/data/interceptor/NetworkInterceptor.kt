package com.eshop.core.data.interceptor

import com.eshop.core.domain.preferences.Preferences
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor(private val preferences: Preferences): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("authorization", preferences.readToken() ?: "")
            .build()
        return chain.proceed(request)
    }
}