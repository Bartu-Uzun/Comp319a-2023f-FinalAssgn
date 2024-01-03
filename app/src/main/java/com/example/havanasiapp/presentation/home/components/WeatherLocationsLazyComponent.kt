package com.example.havanasiapp.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.havanasiapp.R
import com.example.havanasiapp.domain.model.Weather
import com.example.havanasiapp.domain.model.WeatherLocation
import com.example.havanasiapp.ui.theme.HavaNasiAppTheme

@Composable
fun WeatherLocationsLazyComponent(
    weatherLocations: List<WeatherLocation>,
    modifier: Modifier = Modifier,
){

    LazyColumn {
        items(weatherLocations) {weatherLocation: WeatherLocation ->

            WeatherLocationCard(weatherLocation)
        }
    }

}

@Composable
fun WeatherLocationCard(
    weatherLocation: WeatherLocation,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer
            )
            .clip(RoundedCornerShape(size = 8.dp))
    ) {

        Row (
            modifier = modifier
                .fillMaxWidth()
                .padding(2.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){

                NormalTextComponent(
                    value = weatherLocation.city
                )
                NormalTextComponent(
                    value = weatherLocation.country,
                    fontSize = 16
                )
            }

            Row (
                verticalAlignment = Alignment.CenterVertically
            ){

                WeatherIconCard(
                    photoSrc = weatherLocation.currentWeather.icon
                )

                NormalTextComponent(
                    value = "${weatherLocation.currentWeather.temperatureCelcius} °C",
                    fontSize = 16
                )


            }
        }

    }
}

@Composable
fun WeatherIconCard(photoSrc: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(photoSrc)
                .crossfade(true).build(),
            error = painterResource(R.drawable.broken_image),
            placeholder = painterResource(R.drawable.image_holder),
            contentDescription = stringResource(R.string.weather_icon),
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(50.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LocationCardPreview() {
    HavaNasiAppTheme (dynamicColor = false){

        WeatherLocationsLazyComponent(
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