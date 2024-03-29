package com.example.havanasiapp.domain.model.response

import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeather(
    val current: Current,
    val location: Location
)