package com.example.havanasiapp.presentation.home

import com.example.havanasiapp.domain.model.WeatherLocation
import com.example.havanasiapp.presentation.util.ScreenState

data class HomeUIState(
    /* database'de sadece city isimlerini tutsak yeter
    * uygulama her açıldığında her bir city için api call --> get current weather info
    * */
    val screenState: ScreenState = ScreenState.Loading,
    val cityList: List<String> = listOf(),
    val weatherLocations: List<WeatherLocation> = listOf(),
    val cityToAddName: String = "", // todo what happens if this name just does not exist in the world?
    val isAddCityDialogVisible: Boolean = false
)

