package com.example.havanasiapp.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.havanasiapp.data.local.repository.ICityRepository
import com.example.havanasiapp.data.remote.repository.IWeatherRepository
import com.example.havanasiapp.domain.model.City
import com.example.havanasiapp.domain.model.response.CurrentWeather
import com.example.havanasiapp.presentation.util.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cityRepository: ICityRepository,
    private val weatherRepository: IWeatherRepository,
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
            try {
                val cityNameList: List<String> =
                    convertToListOfString(cityRepository.getAllCities())

                val currentWeatherList: MutableList<CurrentWeather> = mutableListOf()

                // TODO apiden current location'ın bilgisini çek


                // TODO apiden citylerin bilgisini çek
                cityNameList.forEach { cityName: String ->

                    val currentWeather: CurrentWeather = weatherRepository.getCurrentWeatherOfCity(cityName)

                    currentWeatherList.add(currentWeather)
                }

                _state.value = state.value.copy( // TODO FOR NOW
                    currentWeatherList = currentWeatherList.toList(),
                    cityNameList = cityNameList,
                    isAddCityDialogVisible = false,
                    screenState = ScreenState.Success
                )

            } catch (e: IOException) {

                _state.value = state.value.copy(
                    screenState = ScreenState.Error(message = e.message ?: "Error")
                )
            } catch (e: HttpException) {

                _state.value = state.value.copy(
                    screenState = ScreenState.Error(message = e.message ?: "Error")
                )
            }
        }
    }



    fun onEvent(event :HomeUIEvent) {

        when (event) {
            is HomeUIEvent.AddNewCity -> {

                // add event.cityName to room db if cityname is not empty
                if (state.value.cityToAddName.isNotEmpty()){

                    val cityToAdd = City(state.value.cityToAddName)

                    addCity(cityToAdd)


                }
            }
            is HomeUIEvent.DeleteCity -> { // TODO

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
                        ResponseEvent.NavigateToDetailScreen(cityName = event.currentWeather.location.region)
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

    private fun addCity(newCity: City) {




        viewModelScope.launch (Dispatchers.IO) {

            try {// add to roomdb
                cityRepository.insertCity(
                    newCity
                )
                // add cityName to state's cityNameList
                // get weather info, add it to state's currentWeatherList
                val newCurrentWeather = weatherRepository.getCurrentWeatherOfCity(newCity.name)

                val cityNameList = state.value.cityNameList.toMutableList()
                val currentWeatherList = state.value.currentWeatherList.toMutableList()
                currentWeatherList.add(newCurrentWeather)
                cityNameList.add(newCity.name)

                _state.value = state.value.copy(
                    currentWeatherList = currentWeatherList.toList(),
                    cityNameList = cityNameList.toList()
                )
            }  catch (e: IOException) {
                _state.value = state.value.copy(
                    screenState = ScreenState.Error(message = e.message ?: "Error")
                )

            } catch (e: HttpException) {
                _state.value = state.value.copy(
                    screenState = ScreenState.Error(message = e.message ?: "Error")
                )

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