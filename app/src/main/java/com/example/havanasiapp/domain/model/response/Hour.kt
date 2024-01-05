package com.example.havanasiapp.domain.model.response

import kotlinx.serialization.Serializable

@Serializable
data class Hour(
    val chance_of_rain: Int,
    val chance_of_snow: Int,
    val condition: Condition,
    val feelslike_c: Double,
    val humidity: Int,
    val is_day: Int,
    val temp_c: Double,
    val time: String,
    val will_it_rain: Int,
    val will_it_snow: Int,
    val wind_mph: Double
)