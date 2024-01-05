package com.example.havanasiapp.domain.model.response

import kotlinx.serialization.Serializable

@Serializable
data class Current(
    val condition: Condition,
    val feelslike_c: Double,
    val humidity: Int,
    val is_day: Int,
    val last_updated: String,
    val temp_c: Double,
    val wind_mph: Double
)