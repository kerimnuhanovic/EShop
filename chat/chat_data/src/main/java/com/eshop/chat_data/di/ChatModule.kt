package com.eshop.chat_data.di

import com.eshop.chat_data.remote.ChatApi
import com.eshop.chat_data.repository.ChatClientImpl
import com.eshop.chat_data.repository.ChatRepositoryImpl
import com.eshop.chat_domain.repository.ChatClient
import com.eshop.chat_domain.repository.ChatRepository
import com.eshop.core.domain.preferences.Preferences
import com.eshop.core.util.BASE_URL
import com.squareup.moshi.Moshi
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
object ChatModule {

    @Provides
    @Singleton
    fun provideChatClient(preferences: Preferences): ChatClient =
        ChatClientImpl(preferences, Moshi.Builder().build())

    @Provides
    @Singleton
    fun provideChatApi(client: OkHttpClient): ChatApi =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(MoshiConverterFactory.create())
            .client(client).build().create()


    @Provides
    @Singleton
    fun provideChatRepository(chatApi: ChatApi): ChatRepository = ChatRepositoryImpl(chatApi)
}