package com.haiphong.weatherforecastapp.network

import com.haiphong.weatherforecastapp.model.Weather
import com.haiphong.weatherforecastapp.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") query: String,
        @Query("units") units: String,
        @Query("appid") appid: String = Constants.API_KEY
    ): Weather
}