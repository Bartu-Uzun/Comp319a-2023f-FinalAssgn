package com.example.havanasiapp.presentation.home

import com.example.havanasiapp.domain.model.WeatherLocation

sealed class HomeUIEvent {
    data class AddNewCity(val cityName: String): HomeUIEvent()
    data class DeleteCity(val cityName: String): HomeUIEvent()
    data class ShowDetails(val weatherLocation: WeatherLocation): HomeUIEvent()
}
