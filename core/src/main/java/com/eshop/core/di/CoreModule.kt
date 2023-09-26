package com.eshop.core.di

import android.app.Application
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import android.content.Context.MODE_PRIVATE
import com.eshop.core.data.preferences.DefaultPreferences
import com.eshop.core.domain.preferences.Preferences
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(application: Application): SharedPreferences =
        application.getSharedPreferences("shared_pref", MODE_PRIVATE)

    @Provides
    @Singleton
    fun providePreferences(sharedPreferences: SharedPreferences): Preferences = DefaultPreferences(sharedPreferences)
}