package com.example.havanasiapp.data.remote.repository

import com.example.havanasiapp.data.remote.WeatherApiService
import com.example.havanasiapp.domain.model.response.CurrentWeather
import com.example.havanasiapp.domain.model.response.WeatherForecast
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApiService
): IWeatherRepository {
    override suspend fun getCurrentWeatherOfCity(city: String): CurrentWeather {
        return api.getCurrentWeatherOfCity(city)
    }

    override suspend fun getWeatherForecastOfCity(city: String, days: Int): WeatherForecast {
        return api.getWeatherForecastOfCity(city = city, days = days)
    }
}