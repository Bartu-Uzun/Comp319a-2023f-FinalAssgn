package com.example.havanasiapp.domain.model.response

import kotlinx.serialization.Serializable


@Serializable
data class Forecastday(
    val astro: Astro,
    val date: String,
    val day: Day,
    val hour: List<Hour>
)