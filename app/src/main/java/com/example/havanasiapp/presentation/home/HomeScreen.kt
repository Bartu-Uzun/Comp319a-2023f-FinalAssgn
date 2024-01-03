package com.example.havanasiapp.presentation.home


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.havanasiapp.R
import com.example.havanasiapp.domain.model.Weather
import com.example.havanasiapp.domain.model.WeatherLocation
import com.example.havanasiapp.presentation.home.components.AddNewCityDialogComponent
import com.example.havanasiapp.presentation.home.components.NormalTextComponent
import com.example.havanasiapp.presentation.home.components.WeatherLocationsLazyComponent
import com.example.havanasiapp.presentation.util.ScreenState
import com.example.havanasiapp.ui.theme.HavaNasiAppTheme

@Composable
fun HomeScreen(
    screenState: ScreenState,
    isAddNewCityDialogVisible: Boolean,
    cityToAddName: String,
    weatherLocations: List<WeatherLocation>,
    onCityToAddChange: (String) -> Unit,
    onClickDelete: (WeatherLocation) -> Unit,
    onSubmitNewCity: () -> Unit,
    onFloatingButtonClicked: () -> Unit,
    onDismissDialogRequest: () -> Unit,
    onRetry: () -> Unit,
) {

    when (screenState) {

        is ScreenState.Loading -> {
            LoadingScreen()
        }
        is ScreenState.Error -> {
            ErrorScreen(
                errorMessage = screenState.message,
                onRetry = onRetry,
            )

        }
        is ScreenState.Success -> {

            SuccessScreen(
                isAddNewCityDialogVisible = isAddNewCityDialogVisible,
                cityToAddName = cityToAddName,
                weatherLocations = weatherLocations,
                onCityToAddChange = onCityToAddChange,
                onClickDelete = onClickDelete,
                onSubmitNewCity = onSubmitNewCity,
                onFloatingButtonClicked = onFloatingButtonClicked,
                onDismissDialogRequest = onDismissDialogRequest,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorScreen(
    errorMessage: String,
    onRetry: () -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                title = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        NormalTextComponent(
                            value = "Hava Nası\'?",
                            fontWeight = FontWeight.Bold,
                            fontSize = 36,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            )
        }
    ) {

        Surface(
            modifier = Modifier
                .padding(it)
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {


                NormalTextComponent(
                    value = errorMessage
                )

                Spacer(modifier = Modifier.height(20.dp))

                IconButton(
                    onClick = onRetry
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "retry",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier
                            .size(150.dp)
                    )

                }

            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadingScreen() {

    Scaffold (
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                title = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        NormalTextComponent(
                            value = "Hava Nası\'?",
                            fontWeight = FontWeight.Bold,
                            fontSize = 36,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            )
        }
    ){

        Surface(
            modifier = Modifier
                .padding(it)
        ) {

            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
            ){

                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.secondary,
                )

            }


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessScreen(
    isAddNewCityDialogVisible: Boolean,
    cityToAddName: String,
    weatherLocations: List<WeatherLocation>,
    onCityToAddChange: (String) -> Unit,
    onClickDelete: (WeatherLocation) -> Unit,
    onSubmitNewCity: () -> Unit,
    onFloatingButtonClicked: () -> Unit,
    onDismissDialogRequest: () -> Unit,
) {

    Scaffold (
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                title = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        NormalTextComponent(
                            value = "Hava Nası\'?",
                            fontWeight = FontWeight.Bold,
                            fontSize = 36,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {},
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = onFloatingButtonClicked,
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add new city",
                            modifier = Modifier
                                .size(56.dp)
                        )

                    }
                }
            )
        }
    ){
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            Column {
                WeatherLocationsLazyComponent(
                    weatherLocations = weatherLocations,
                    onClickDelete = onClickDelete
                )

                if (isAddNewCityDialogVisible) {
                    AddNewCityDialogComponent(
                        cityToAddName = cityToAddName,
                        onSubmitNewCity = onSubmitNewCity,
                        onCityToAddChange = onCityToAddChange,
                        onDismissRequest = onDismissDialogRequest,

                    )
                }
            }


        }
    }


}


@Preview(showBackground = true)
@Composable
fun SuccessScreenPreview() {
    HavaNasiAppTheme (dynamicColor = false){

        HomeScreen(
            screenState = ScreenState.Success,
            isAddNewCityDialogVisible = false,
            cityToAddName = "",
            onClickDelete = {},
            onRetry = {},
            onFloatingButtonClicked = {},
            onDismissDialogRequest = {},
            onCityToAddChange = {str ->},
            onSubmitNewCity = {},
            weatherLocations = listOf(
                WeatherLocation(
                    city = "Ankara",
                    country = "Turkey",
                    currentWeather = Weather(
                        temperatureCelcius = 10.0f,
                        description = "Partly cloudy",
                        humidity = 82,
                        icon = "//cdn.weatherapi.com/weather/64x64/day/116.png",
                        windMph = 10.5f
                    )
                ),
                WeatherLocation(
                    city = "Istanbul",
                    country = "Turkey",
                    currentWeather = Weather(
                        temperatureCelcius = 15.5f,
                        description = "Sunny",
                        humidity = 80,
                        icon = "//cdn.weatherapi.com/weather/64x64/day/116.png",
                        windMph = 10.5f
                    )
                )

            )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    HavaNasiAppTheme (dynamicColor = false){

        HomeScreen(
            screenState = ScreenState.Loading,
            isAddNewCityDialogVisible = true,
            cityToAddName = "",
            onClickDelete = {},
            onRetry = {},
            onFloatingButtonClicked = {},
            onDismissDialogRequest = {},
            onCityToAddChange = {str ->},
            onSubmitNewCity = {},
            weatherLocations = listOf(
                WeatherLocation(
                    city = "Ankara",
                    country = "Turkey",
                    currentWeather = Weather(
                        temperatureCelcius = 10.0f,
                        description = "Partly cloudy",
                        humidity = 82,
                        icon = "//cdn.weatherapi.com/weather/64x64/day/116.png",
                        windMph = 10.5f
                    )
                ),
                WeatherLocation(
                    city = "Istanbul",
                    country = "Turkey",
                    currentWeather = Weather(
                        temperatureCelcius = 15.5f,
                        description = "Sunny",
                        humidity = 80,
                        icon = "//cdn.weatherapi.com/weather/64x64/day/116.png",
                        windMph = 10.5f
                    )
                )

            )
        )
    }
}