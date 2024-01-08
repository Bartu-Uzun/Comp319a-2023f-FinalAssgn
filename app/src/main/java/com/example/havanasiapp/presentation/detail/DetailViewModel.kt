package com.example.havanasiapp.presentation.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.havanasiapp.data.remote.repository.IWeatherRepository
import com.example.havanasiapp.presentation.util.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val weatherRepository: IWeatherRepository,
    savedStateHandle: SavedStateHandle,
): ViewModel() {


    private val _state = mutableStateOf(DetailUIState())
    val state: State<DetailUIState> = _state

    init {

        val location = savedStateHandle.get<String>("location")!!

        setContent(location = location)
    }

    private fun setContent(
        location: String = state.value.location,
        days: Int = state.value.days
    ) {

        viewModelScope.launch {

            try {
                val weatherForecast = weatherRepository.getWeatherForecastOfCity(location, days)

                _state.value = state.value.copy(
                    weatherForecast = weatherForecast,
                    location = location,
                    screenState = ScreenState.Success,
                )
            } catch (e: IOException) {

                _state.value = state.value.copy(
                    screenState = ScreenState.Error(message = e.message ?: "Error"),
                    location = location,
                )
            } catch (e: HttpException) {

                _state.value = state.value.copy(
                    screenState = ScreenState.Error(message = e.message ?: "Error"),
                    location = location,
                )
            }



        }

    }

    fun onEvent(event: DetailUIEvent) {

        when (event) {

            is DetailUIEvent.Retry -> {

                setContent()
            }
        }
    }

}