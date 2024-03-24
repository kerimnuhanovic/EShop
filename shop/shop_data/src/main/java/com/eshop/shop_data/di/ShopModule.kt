package com.eshop.shop_data.di

import com.eshop.core.util.BASE_URL
import com.eshop.shop_data.remote.ShopApi
import com.eshop.shop_data.repository.ShopRepositoryImpl
import com.eshop.shop_domain.repository.ShopRepository
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
object ShopModule {

    @Provides
    @Singleton
    fun provideShopApi(client: OkHttpClient): ShopApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build().create()

    @Provides
    @Singleton
    fun provideShopRepository(shopApi: ShopApi): ShopRepository = ShopRepositoryImpl(shopApi)
}