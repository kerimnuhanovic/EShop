package com.eshop.signup_data.di

import com.eshop.core.util.BASE_URL
import com.eshop.signup_data.remote.SignupApi
import com.eshop.signup_data.repository.SignupRepositoryImpl
import com.eshop.signup_domain.repository.SignupRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SignupDataModuleModule {

    @Provides
    @Singleton
    fun provideSignupApi(client: OkHttpClient): SignupApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build().create()

    @Provides
    @Singleton
    fun provideSignupRepository(signupApi: SignupApi): SignupRepository = SignupRepositoryImpl(signupApi)
}