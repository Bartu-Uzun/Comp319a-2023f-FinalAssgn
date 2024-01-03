package com.example.havanasiapp.presentation.home

import com.example.havanasiapp.domain.model.WeatherLocation

data class HomeUIState(
    /* database'de sadece city isimlerini tutsak yeter
    * uygulama her açıldığında her bir city için api call --> get current weather info
    * */
    val cities: List<String>,
    val cityWeathers: List<WeatherLocation>,
    val cityToAddName: String, // todo what happens if this name just does not exist in the world?
)
