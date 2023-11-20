package com.haiphong.weatherforecastapp.model

data class WeatherItem(
    val clouds: Int,
    val deg: Int,
    val dt: Long,
    val feels_like: FeelsLike,
    val gust: Double,
    val humidity: Int,
    val pop: Double,
    val pressure: Int,
    val speed: Double,
    val sunrise: Long,
    val sunset: Long,
    val temp: Temp,
    val weather: List<WeatherObject>
)