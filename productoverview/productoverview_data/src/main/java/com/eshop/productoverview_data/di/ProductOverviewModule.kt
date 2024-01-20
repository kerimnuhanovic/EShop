package com.eshop.productoverview_data.di

import com.eshop.core.util.BASE_URL
import com.eshop.productoverview_data.remote.ProductOverviewApi
import com.eshop.productoverview_data.repository.ProductOverviewRepositoryImpl
import com.eshop.productoverview_domain.repository.ProductOverviewRepository
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
object ProductOverviewModule {


    @Provides
    @Singleton
    fun provideProductApi(client: OkHttpClient): ProductOverviewApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build().create()

    @Provides
    @Singleton
    fun provideProductOverviewRepository(productOverviewApi: ProductOverviewApi): ProductOverviewRepository = ProductOverviewRepositoryImpl(productOverviewApi)
}