package com.example.havanasiapp.domain.model.response

import kotlinx.serialization.Serializable


@Serializable
data class WeatherForecast(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)