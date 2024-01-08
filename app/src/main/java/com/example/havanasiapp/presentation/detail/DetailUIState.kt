package com.example.havanasiapp.presentation.detail

import com.example.havanasiapp.domain.model.response.WeatherForecast
import com.example.havanasiapp.presentation.util.ScreenState

data class DetailUIState(
    val screenState: ScreenState = ScreenState.Loading,
    val location: String = "",
    val days: Int = 3,
    val weatherForecast: WeatherForecast? = null,
)
