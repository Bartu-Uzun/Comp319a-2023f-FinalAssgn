package com.example.havanasiapp.data.remote

import com.example.havanasiapp.domain.model.response.CurrentWeather
import com.example.havanasiapp.domain.model.response.WeatherForecast
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("current.json")
    suspend fun getCurrentWeatherOfCity(
        @Query("q") city: String,
    ): CurrentWeather

    // TODO getCurrentWeatherOfLocation

    @GET("forecast.json")
    suspend fun getWeatherForecastOfCity(
        @Query("key") key: String,
        @Query("q") city: String,
        @Query("days") days: Int,
    ): WeatherForecast

    // TODO getCurrentWeatherOfLocation
}