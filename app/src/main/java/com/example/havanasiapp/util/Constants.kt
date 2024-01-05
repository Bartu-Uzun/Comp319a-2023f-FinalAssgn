package com.example.havanasiapp.util

import okhttp3.MediaType.Companion.toMediaType

object Constants {

    const val BASE_URL = "https://api.weatherapi.com/v1/"
    const val API_KEY = "e0be8216d5294e2b81184612240301"
    val contentType = "application/json".toMediaType()
}