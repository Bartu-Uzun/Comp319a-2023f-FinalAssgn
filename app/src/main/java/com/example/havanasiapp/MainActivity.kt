package com.example.havanasiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.havanasiapp.domain.model.response.CurrentWeather
import com.example.havanasiapp.presentation.detail.DetailScreen
import com.example.havanasiapp.presentation.detail.DetailUIEvent
import com.example.havanasiapp.presentation.detail.DetailViewModel
import com.example.havanasiapp.presentation.home.HomeScreen
import com.example.havanasiapp.presentation.home.HomeUIEvent
import com.example.havanasiapp.presentation.home.HomeViewModel
import com.example.havanasiapp.ui.theme.HavaNasiAppTheme
import com.example.havanasiapp.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HavaNasiAppTheme (dynamicColor = false){
                // A surface container using the 'background' color from the theme

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.HomeScreen.route
                ) {
                    composable(route = Screen.HomeScreen.route) {

                        val homeViewModel: HomeViewModel = hiltViewModel()
                        val homeUIState = homeViewModel.state.value
                        val homeResponseEvents = homeViewModel.responseEvents

                        HomeScreen(
                            screenState = homeUIState.screenState,
                            responseEvents = homeResponseEvents,
                            isAddNewCityDialogVisible = homeUIState.isAddCityDialogVisible,
                            cityToAddName = homeUIState.cityToAddName,
                            currentWeatherList = homeUIState.currentWeatherList,
                            isAddingNameError = homeUIState.isAddingNameError,
                            isConnectionError = homeUIState.isConnectionError,
                            addingErrorMessage = homeUIState.addingErrorMessage,
                            onCityToAddChange = { cityToAddName: String ->

                                homeViewModel.onEvent(HomeUIEvent.SetCityToAddName(cityToAddName=cityToAddName))

                            },
                            onClickDelete = {currentWeather: CurrentWeather ->
                                homeViewModel.onEvent(HomeUIEvent.DeleteCity(cityName = currentWeather.location.name))

                            },
                            onClickWeatherCard = {currentWeather: CurrentWeather ->
                                homeViewModel.onEvent(HomeUIEvent.ShowDetails(currentWeather = currentWeather))
                            },
                            onSubmitNewCity = {
                                homeViewModel.onEvent(HomeUIEvent.AddNewCity)
                                              },
                            onFloatingButtonClicked = {
                                homeViewModel.onEvent(HomeUIEvent.ToggleAddCityDialog(isVisible = true))
                                                      },
                            onDismissDialogRequest = {
                                homeViewModel.onEvent(HomeUIEvent.ToggleAddCityDialog(isVisible = false))
                                                     },
                            onRetry = {
                                homeViewModel.onEvent(HomeUIEvent.Retry)

                            },
                            onNavigateToDetailScreen = {location: String ->
                                navController.navigate(Screen.DetailScreen.route + "/$location")
                            }
                        )

                    }

                    composable(
                        route = Screen.DetailScreen.route + "/{location}",
                        arguments = listOf(
                            navArgument(name = "location") {
                                type = NavType.StringType
                                nullable = false
                            },
                        )
                    ) {

                        val detailViewModel: DetailViewModel = hiltViewModel()
                        val detailUIState = detailViewModel.state.value

                        DetailScreen(
                            screenState = detailUIState.screenState,
                            weatherForecast = detailUIState.weatherForecast,
                            onRetry = {
                                detailViewModel.onEvent(DetailUIEvent.Retry)
                                      },
                            onClickBackButton = {
                                navController.popBackStack()
                            }
                        )

                    }
                }
            }
        }
    }
}
