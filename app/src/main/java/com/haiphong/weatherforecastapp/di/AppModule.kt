package com.haiphong.weatherforecastapp.di

import android.content.Context
import androidx.room.Room
import com.haiphong.weatherforecastapp.data.FavoriteDao
import com.haiphong.weatherforecastapp.data.FavoriteDatabase
import com.haiphong.weatherforecastapp.network.WeatherApi
import com.haiphong.weatherforecastapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOpenWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(favoriteDatabase: FavoriteDatabase): FavoriteDao =
        favoriteDatabase.favoriteDao()

    @Provides
    @Singleton
    fun provideFavoriteDatabase(@ApplicationContext context: Context): FavoriteDatabase =
        Room.databaseBuilder(
            context,
            FavoriteDatabase::class.java,
            "favorite_database"
        ).fallbackToDestructiveMigration().build()

}