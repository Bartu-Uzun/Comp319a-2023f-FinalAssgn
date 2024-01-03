package com.example.havanasiapp.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.havanasiapp.domain.model.WeatherLocation
import com.example.havanasiapp.presentation.home.components.WeatherLocationsLazyComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    weatherLocations: List<WeatherLocation>,
) {

    Scaffold {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            WeatherLocationsLazyComponent(
                weatherLocations = weatherLocations
            )

        }
    }


}