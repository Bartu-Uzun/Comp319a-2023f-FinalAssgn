package com.example.havanasiapp.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.havanasiapp.data.local.repository.ICityRepository
import com.example.havanasiapp.domain.model.City
import com.example.havanasiapp.presentation.util.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cityRepository: ICityRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf(HomeUIState())
    val state: State<HomeUIState> = _state

    private val responseEventChannel = Channel<ResponseEvent>()
    val responseEvents = responseEventChannel.receiveAsFlow()

    init {
        setContent()
    }

    private fun setContent() {

        // roomdb'den city isimlerini çek
        viewModelScope.launch (Dispatchers.IO){
            val cityList: List<String> = convertToListOfString(cityRepository.getAllCities())

            // TODO apiden current location'ın bilgisini çek

            // TODO apiden citylerin bilgisini çek (try block içinde)

            _state.value = state.value.copy( // TODO FOR NOW
                cityList = cityList,
                isAddCityDialogVisible = false,
                screenState = ScreenState.Success
            )
        }






    }



    fun onEvent(event :HomeUIEvent) {

        when (event) {
            is HomeUIEvent.AddNewCity -> {

                // add event.cityName to room db if cityname is not empty
                if (state.value.cityToAddName.isNotEmpty()){

                    viewModelScope.launch (Dispatchers.IO){
                        cityRepository.insertCity(
                            City(state.value.cityToAddName)
                        )

                        setContent()
                    }


                }
            }
            is HomeUIEvent.DeleteCity -> {

                // remove event.cityName from room db
                viewModelScope.launch (Dispatchers.IO){
                    cityRepository.deleteCity(
                        City(event.cityName)
                    )

                    setContent()
                }


            }
            is HomeUIEvent.ShowDetails -> {

                // todo subscribe to the channel in ui
                viewModelScope.launch{
                    responseEventChannel.send(
                        ResponseEvent.NavigateToDetailScreen(cityName = event.weatherLocation.city)
                    )
                }
            }
            is HomeUIEvent.ToggleAddCityDialog -> {

                _state.value = state.value.copy(
                    isAddCityDialogVisible = event.isVisible
                )
            }

            is HomeUIEvent.SetCityToAddName -> {

                _state.value = state.value.copy(
                    cityToAddName = event.cityToAddName
                )
            }
            is HomeUIEvent.Retry -> {
                _state.value = state.value.copy(
                    screenState = ScreenState.Loading
                )

                setContent()
            }
        }
    }


    private fun convertToListOfString(cityList: List<City>): List<String> {

        val stringList: MutableList<String> = mutableListOf()

        cityList.forEach { city: City ->

            stringList.add(city.name)

        }
        return stringList.toList()
    }

    sealed class ResponseEvent {

        data class NavigateToDetailScreen(val cityName: String): ResponseEvent()
    }
}