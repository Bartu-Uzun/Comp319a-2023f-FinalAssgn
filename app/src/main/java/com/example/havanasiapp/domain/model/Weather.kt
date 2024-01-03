package com.example.havanasiapp.domain.model

data class Weather(
    val temperatureCelcius: Float,
    val description: String, // i.e. partly cloudy
    val icon: String, // fornow, its the link of the icon
    val windMph: Float,
    val humidity: Int
)
