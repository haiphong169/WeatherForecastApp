package com.haiphong.weatherforecastapp.repository

import com.haiphong.weatherforecastapp.data.DataOrException
import com.haiphong.weatherforecastapp.data.Favorite
import com.haiphong.weatherforecastapp.data.FavoriteDao
import com.haiphong.weatherforecastapp.model.Weather
import com.haiphong.weatherforecastapp.network.WeatherApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: WeatherApi,
    private val favoriteDao: FavoriteDao
) {

    suspend fun getWeather(city: String, units: String = "metric")
            : DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(query = city, units = units)
        } catch (e: Exception) {
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }

    fun getFavorites(): Flow<List<Favorite>> = favoriteDao.getFavorites()
    fun checkIfFavorite(city: String): Flow<Boolean> = favoriteDao.checkIfFavorite(city)
    suspend fun addFavorite(favorite: Favorite) = favoriteDao.addFavorite(favorite)
    suspend fun deleteFavorite(favorite: Favorite) = favoriteDao.deleteFavorite(favorite)
    suspend fun flush() = favoriteDao.flush()


}