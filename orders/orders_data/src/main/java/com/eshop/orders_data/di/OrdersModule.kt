package com.eshop.orders_data.di

import com.eshop.core.util.BASE_URL
import com.eshop.orders_data.network.OrdersApi
import com.eshop.orders_data.repository.OrdersRepositoryImpl
import com.eshop.orders_domain.repository.OrdersRepository
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
object OrdersModule {

    @Provides
    @Singleton
    fun provideOrdersApi(client: OkHttpClient): OrdersApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build().create()

    @Singleton
    @Provides
    fun provideOrdersRepository(ordersApi: OrdersApi) : OrdersRepository = OrdersRepositoryImpl(ordersApi)
}