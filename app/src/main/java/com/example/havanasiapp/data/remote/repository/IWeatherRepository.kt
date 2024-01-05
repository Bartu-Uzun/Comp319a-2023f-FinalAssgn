package com.example.havanasiapp.data.remote.repository

import com.example.havanasiapp.domain.model.response.CurrentWeather

interface IWeatherRepository {

    suspend fun getCurrentWeatherOfCity(city: String): CurrentWeather
}