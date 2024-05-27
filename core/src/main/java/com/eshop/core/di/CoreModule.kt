package com.eshop.core.di

import android.app.Application
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import android.content.Context.MODE_PRIVATE
import com.eshop.core.data.preferences.DefaultPreferences
import com.eshop.core.data.remote.FavouriteProductApi
import com.eshop.core.data.repository.FavouriteProductRepositoryImpl
import com.eshop.core.domain.preferences.Preferences
import com.eshop.core.domain.repository.FavouriteProductRepository
import com.eshop.core.util.BASE_URL
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(application: Application): SharedPreferences =
        application.getSharedPreferences("shared_pref", MODE_PRIVATE)

    @Provides
    @Singleton
    fun providePreferences(sharedPreferences: SharedPreferences): Preferences =
        DefaultPreferences(sharedPreferences)

    @Provides
    @Singleton
    fun provideFavouriteProductApi(client: OkHttpClient): FavouriteProductApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client).build().create()

    @Provides
    @Singleton
    fun provideFavouriteProductRepository(favouriteProductApi: FavouriteProductApi): FavouriteProductRepository = FavouriteProductRepositoryImpl(favouriteProductApi)
}