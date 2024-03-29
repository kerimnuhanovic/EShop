package com.eshop.product_data.di

import com.eshop.core.util.BASE_URL
import com.eshop.product_data.remote.ProductApi
import com.eshop.product_data.repository.ProductRepositoryImpl
import com.eshop.product_domain.repository.ProductRepository
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
object ProductModule {

    @Provides
    @Singleton
    fun provideProductApi(client: OkHttpClient): ProductApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build().create()

    @Provides
    @Singleton
    fun provideProductRepository(productApi: ProductApi): ProductRepository = ProductRepositoryImpl(productApi)
}