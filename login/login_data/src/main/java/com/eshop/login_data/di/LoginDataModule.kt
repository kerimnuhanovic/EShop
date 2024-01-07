package com.eshop.login_data.di

import com.eshop.core.data.interceptor.NetworkInterceptor
import com.eshop.core.domain.preferences.Preferences
import com.eshop.core.util.BASE_URL
import com.eshop.login_data.remote.LoginApi
import com.eshop.login_data.repository.LoginRepositoryImpl
import com.eshop.login_domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginDataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(preferences: Preferences): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(NetworkInterceptor(preferences))
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideLoginApi(client: OkHttpClient): LoginApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build().create()

    @Provides
    @Singleton
    fun provideLoginRepository(loginApi: LoginApi): LoginRepository = LoginRepositoryImpl(loginApi)
}