package com.eshop.cart_data.di

import com.eshop.cart_data.remote.CartApi
import com.eshop.cart_data.repository.CartRepositoryImpl
import com.eshop.cart_domain.repository.CartRepository
import com.eshop.core.util.BASE_URL
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
object CartModule {

    @Provides
    @Singleton
    fun provideCartApi(client: OkHttpClient): CartApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create()

    @Provides
    @Singleton
    fun provideCartRepository(cartApi: CartApi): CartRepository = CartRepositoryImpl(cartApi)
}