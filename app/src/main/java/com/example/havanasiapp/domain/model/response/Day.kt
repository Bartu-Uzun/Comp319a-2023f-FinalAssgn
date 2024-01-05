package com.example.havanasiapp.domain.model.response

import kotlinx.serialization.Serializable

@Serializable
data class Day(
    val avghumidity: Double,
    val avgtemp_c: Double,
    val condition: Condition,
    val daily_chance_of_rain: Int,
    val daily_will_it_rain: Int,
    val maxtemp_c: Double,
    val maxwind_mph: Double,
    val mintemp_c: Double
)