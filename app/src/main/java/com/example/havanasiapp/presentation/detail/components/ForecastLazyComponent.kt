package com.example.havanasiapp.presentation.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.havanasiapp.domain.model.response.Forecastday
import com.example.havanasiapp.presentation.home.components.NormalTextComponent
import com.example.havanasiapp.presentation.home.components.WeatherIconCard
import com.example.havanasiapp.ui.theme.HavaNasiAppTheme
import com.example.havanasiapp.util.Utils

@Composable
fun ForecastLazyComponent(
    forecastdayList: List<Forecastday>,
    modifier: Modifier = Modifier,
) {

    LazyColumn {
        items(forecastdayList) {forecastDay: Forecastday ->

            forecastCard(
                dayOfWeek = Utils.getDayOfWeek(forecastDay.date),
                photoSrc = forecastDay.day.condition.icon,
                maxtemp = forecastDay.day.maxtemp_c,
                mintemp = forecastDay.day.mintemp_c,
            )

        }
    }

}

@Composable
fun forecastCard(
    modifier: Modifier = Modifier,
    dayOfWeek: String,
    photoSrc: String,
    maxtemp: Double,
    mintemp: Double,
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(size = 8.dp)),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    ) {

                    NormalTextComponent(
                        value = dayOfWeek,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    Spacer(modifier = Modifier.width(36.dp))


                    WeatherIconCard(
                        photoSrc = photoSrc
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column (
                        horizontalAlignment = Alignment.Start
                    ){
                        NormalTextComponent(
                            value = "Daily Max: $maxtemp °C",
                            fontWeight = FontWeight.Normal,
                            fontSize = 16,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        NormalTextComponent(
                            modifier = Modifier
                                .alpha(0.5f),
                            value = "Daily Min: $mintemp °C",
                            fontWeight = FontWeight.Normal,
                            fontSize = 16,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun forecastCardPreview() {
    HavaNasiAppTheme (dynamicColor = false){

        forecastCard(
            dayOfWeek = "MON",
            photoSrc = "//cdn.weatherapi.com/weather/64x64/day/326.png",
            maxtemp = 15.4,
            mintemp = 6.7
        )
    }
}