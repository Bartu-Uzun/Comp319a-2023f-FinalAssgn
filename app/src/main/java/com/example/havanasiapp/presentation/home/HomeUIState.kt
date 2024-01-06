package com.example.havanasiapp.presentation.home


import com.example.havanasiapp.domain.model.response.CurrentWeather
import com.example.havanasiapp.presentation.util.ScreenState

data class HomeUIState(
    /* database'de sadece city isimlerini tutsak yeter
    * uygulama her açıldığında her bir city için api call --> get current weather info
    * */
    val screenState: ScreenState = ScreenState.Loading,
    val cityNameList: List<String> = listOf(),
    val currentWeatherList: List<CurrentWeather> = listOf(),
    val cityToAddName: String = "", // todo what happens if this name just does not exist in the world?
    val isAddCityDialogVisible: Boolean = false,
    val addingErrorMessage: String = "",
    val isAddingNameError: Boolean = false,
    val isConnectionError: Boolean = false,
)

