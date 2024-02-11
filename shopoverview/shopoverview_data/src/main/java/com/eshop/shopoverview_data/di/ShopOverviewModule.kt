package com.eshop.shopoverview_data.di

import com.eshop.core.util.BASE_URL
import com.eshop.shopoverview_data.remote.ShopOverviewApi
import com.eshop.shopoverview_data.repository.ShopOverviewRepositoryImpl
import com.eshop.shopoverview_domain.repository.ShopOverviewRepository
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
object ShopOverviewModule {

    @Singleton
    @Provides
    fun provideShopOverviewApi(client: OkHttpClient): ShopOverviewApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create()

    @Singleton
    @Provides
    fun provideShopOverviewRepository(shopOverviewApi: ShopOverviewApi): ShopOverviewRepository = ShopOverviewRepositoryImpl(shopOverviewApi)


}