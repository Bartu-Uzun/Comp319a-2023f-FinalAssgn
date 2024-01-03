package com.example.havanasiapp.domain.model

data class WeatherLocation(
    val city: String,
    val country: String, // todo
    val currentWeather: Weather
)
