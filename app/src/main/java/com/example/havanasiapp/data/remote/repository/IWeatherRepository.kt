package com.example.havanasiapp.data.remote.repository

import com.example.havanasiapp.domain.model.response.CurrentWeather
import com.example.havanasiapp.domain.model.response.WeatherForecast

interface IWeatherRepository {

    suspend fun getCurrentWeatherOfCity(city: String): CurrentWeather

    suspend fun getWeatherForecastOfCity( city: String, days: Int): WeatherForecast
}