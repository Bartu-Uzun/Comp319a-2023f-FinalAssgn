package com.example.havanasiapp.domain.model.response

import kotlinx.serialization.Serializable


@Serializable
data class Forecast(
    val forecastday: List<Forecastday>
)