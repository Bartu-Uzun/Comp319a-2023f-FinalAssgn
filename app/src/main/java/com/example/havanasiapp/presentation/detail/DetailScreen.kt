package com.example.havanasiapp.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import com.example.havanasiapp.domain.model.response.Hour
import com.example.havanasiapp.domain.model.response.WeatherForecast
import com.example.havanasiapp.presentation.detail.components.ForecastLazyComponent
import com.example.havanasiapp.presentation.home.components.NormalTextComponent
import com.example.havanasiapp.presentation.home.components.WeatherIconCard
import com.example.havanasiapp.presentation.util.ScreenState
import com.example.havanasiapp.util.Utils
import java.time.LocalTime

@Composable
fun DetailScreen(
    screenState: ScreenState,
    weatherForecast: WeatherForecast?,
    onRetry: () -> Unit,
    onClickBackButton: () -> Unit,
) {

    when (screenState) {
        is ScreenState.Error -> {
            ErrorScreen(
                errorMessage = screenState.message,
                onRetry = onRetry,
                onClickBackButton = onClickBackButton,
            )
        }
        is ScreenState.Loading -> {
            LoadingScreen()
        }
        is ScreenState.Success -> {
            SuccessScreen(
                weatherForecast = weatherForecast!!,
                onClickBackButton = onClickBackButton,
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessScreen(
    weatherForecast: WeatherForecast,
    onClickBackButton: () -> Unit,
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
                },
                navigationIcon = {
                    IconButton(
                        onClick = onClickBackButton
                    ) {

                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate back"
                        )

                    }
                }
            )
        },
    ) {

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            Column (
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Card (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .padding(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ){

                    Column (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        NormalTextComponent(
                            value = weatherForecast.location.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 32
                        )
                        NormalTextComponent(
                            value = weatherForecast.location.country,
                            fontSize = 18
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Column (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        WeatherIconCard(
                            photoSrc = weatherForecast.current.condition.icon
                        )
                        NormalTextComponent(
                            value = "${weatherForecast.current.temp_c} °C",
                            fontSize = 28,
                            fontWeight = FontWeight.SemiBold
                        )

                        NormalTextComponent(
                            value = weatherForecast.current.condition.text,
                            fontSize = 18,
                        )
                    }

                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){

                        NormalTextComponent(
                            value = "humidity: ${weatherForecast.current.humidity}",
                            fontSize = 18,
                        )

                        NormalTextComponent(
                            value = "wind: ${weatherForecast.current.wind_mph.toInt()} mph",
                            fontSize = 18
                        )
                    }
                }

                Card (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .padding(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {

                    LazyRow() {

                        val currentHour = LocalTime.now().hour

                        val todayForeCastHourList: List<Hour> =
                            weatherForecast.forecast.forecastday[0].hour.filter { hour: Hour ->
                                Utils.getHourValue(hour) >= currentHour
                            }


                        items(todayForeCastHourList) {hour: Hour ->

                            ForeCastDayCard(hour)
                        }
                    }
                }

                Card (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .padding(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {

                    ForecastLazyComponent(
                        forecastdayList = weatherForecast.forecast.forecastday,
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
                },
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
fun ErrorScreen(
    errorMessage: String,
    onRetry: () -> Unit,
    onClickBackButton: () -> Unit,
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
                },
                navigationIcon = {
                    IconButton(
                        onClick = onClickBackButton
                    ) {

                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate back"
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



@Composable
fun ForeCastDayCard(hour: Hour) {

    Card (
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = Modifier
            .padding(top = 8.dp)
            .padding(8.dp)
    ) {

        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            NormalTextComponent(
                value = Utils.getHourValue(hour).toString(),
                fontSize = 18
            )

            WeatherIconCard(
                photoSrc = hour.condition.icon
            )

            NormalTextComponent(
                value = "${hour.temp_c} °C",
                fontSize = 18,
                fontWeight = FontWeight.SemiBold
            )
        }

    }
}


/*
@Preview(showBackground = true)
@Composable
fun DetailScreenDialogPreview() {
    HavaNasiAppTheme (dynamicColor = false){



        ForeCastDayCard(
            hour = Hour(
                chance_of_rain = 50,
                chance_of_snow = 1,
                condition = Condition(
                    code = 100,
                    icon = "",
                    text = "Cloudy"
                ),
                feelslike_c = 10.2,
                humidity = 80,
                is_day = 1,
                temp_c = 10.0,
                wind_mph = 10.0,
                time = "2024-01-07 00:00",
                will_it_rain = 0,
                will_it_snow = 0,
            )
        )

                forecast = Forecast(
                    forecastday = listOf(
                        Forecastday(
                            astro = Astro(),
                            date = "2024-01-07",
                            day = Day(
                                maxtemp_c = 13.2,
                                mintemp_c = 3.5,
                                avgtemp_c = 7.6,
                            maxwind_mph = 21.3,
                            avghumidity = 62.0,
                            daily_will_it_rain = 1,
                            daily_chance_of_rain = 87,
                            condition = Condition(
                                text = "Patchy rain possible",
                                icon = "//cdn.weatherapi.com/weather/64x64/day/176.png",
                                code = 1063
                            )
                            ),
                            hour = listOf(
                                Hour(
                                    chance_of_rain = 50,
                                    chance_of_snow = 1,
                                    condition = Condition(
                                        code = 100,
                                        icon = "",
                                        text = "Cloudy"
                                    ),
                                    feelslike_c = 10.2,
                                    humidity = 80,
                                    is_day = 1,
                                    temp_c = 10.0,
                                    wind_mph = 10.0,
                                    time = "2024-01-07 18:00",
                                    will_it_rain = 0,
                                    will_it_snow = 0,
                                ),
                                Hour(
                                    chance_of_rain = 50,
                                    chance_of_snow = 1,
                                    condition = Condition(
                                        code = 100,
                                        icon = "",
                                        text = "Cloudy"
                                    ),
                                    feelslike_c = 10.2,
                                    humidity = 80,
                                    is_day = 1,
                                    temp_c = 10.0,
                                    wind_mph = 10.0,
                                    time = "2024-01-07 19:00",
                                    will_it_rain = 0,
                                    will_it_snow = 0,
                                ),
                                Hour(
                                    chance_of_rain = 50,
                                    chance_of_snow = 1,
                                    condition = Condition(
                                        code = 100,
                                        icon = "",
                                        text = "Cloudy"
                                    ),
                                    feelslike_c = 10.2,
                                    humidity = 80,
                                    is_day = 1,
                                    temp_c = 10.0,
                                    wind_mph = 10.0,
                                    time = "2024-01-07 20:00",
                                    will_it_rain = 0,
                                    will_it_snow = 0,
                                )
                            )
                        )

                    )
                ),
                location = Location(
                    country = "Turkey",
                    region = "Turkey",
                    name = "Ankara",
                    lat = 10.00,
                    lon = 11.00,
                    localtime = "10:00",
                    localtime_epoch = 10,
                    tz_id = "tzzzid"
                )
            )
        )


    }
}

 */