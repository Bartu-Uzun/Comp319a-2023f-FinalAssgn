package com.example.havanasiapp.presentation.home.components



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.havanasiapp.R
import com.example.havanasiapp.domain.model.response.CurrentWeather
import com.example.havanasiapp.ui.theme.HavaNasiAppTheme

@Composable
fun WeatherLocationsLazyComponent(
    currentWeatherList: List<CurrentWeather>,
    modifier: Modifier = Modifier,
    onClickDelete: (CurrentWeather) -> Unit,
    onClickWeatherCard: (CurrentWeather) -> Unit,
){

    LazyColumn {
        items(currentWeatherList) {currentWeather: CurrentWeather ->

            currentWeatherCard(
                currentWeather = currentWeather,
                onClickDelete = onClickDelete,
                onClickWeatherCard = onClickWeatherCard,
                )
        }
    }

}

@Composable
fun currentWeatherCard(
    currentWeather: CurrentWeather,
    modifier: Modifier = Modifier,
    onClickDelete: (CurrentWeather) -> Unit,
    onClickWeatherCard: (CurrentWeather) -> Unit,
) {

    Card(
        modifier = modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(size = 8.dp))
            .clickable(
                onClick = {
                    onClickWeatherCard(currentWeather)
                }
            ),
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
        ){
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .size(width = 100.dp, height = 72.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,

                ) {

                    NormalTextComponent(
                        value = currentWeather.location.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    NormalTextComponent(
                        value = currentWeather.location.country,
                        fontSize = 14,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    WeatherIconCard(
                        photoSrc = currentWeather.current.condition.icon
                    )

                    NormalTextComponent(
                        value = "${currentWeather.current.temp_c} °C",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )


                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {

                    IconButton(
                        modifier = Modifier.size(34.dp),
                        onClick = { onClickDelete(currentWeather) }
                    ) {
                        Icon(
                            modifier = Modifier.size(34.dp),
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete city",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        
                    }
                }
            }
        }

    }
}

@Composable
fun WeatherIconCard(photoSrc: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,

    ) {

        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data("https:$photoSrc")
                .crossfade(true).build(),
            error = painterResource(R.drawable.broken_image),
            placeholder = painterResource(R.drawable.image_holder),
            contentDescription = stringResource(R.string.weather_icon),
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(64.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LocationCardPreview() {
    HavaNasiAppTheme (dynamicColor = false){

        WeatherLocationsLazyComponent(
            onClickDelete = {},
            currentWeatherList = listOf(),
            onClickWeatherCard = {}
        )
    }
}