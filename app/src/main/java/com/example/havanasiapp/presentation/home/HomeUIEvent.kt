package com.example.havanasiapp.presentation.home

import com.example.havanasiapp.domain.model.WeatherLocation

sealed class HomeUIEvent {
    data class SetCityToAddName(val cityToAddName: String): HomeUIEvent()
    data class ToggleAddCityDialog(val isVisible: Boolean): HomeUIEvent()
    data class DeleteCity(val cityName: String): HomeUIEvent()
    data class ShowDetails(val weatherLocation: WeatherLocation): HomeUIEvent()
    object AddNewCity: HomeUIEvent()
    object Retry: HomeUIEvent()
}
