package com.example.havanasiapp.presentation.util

sealed class ScreenState {

    data class Error(val message: String): ScreenState()

    object Loading: ScreenState()
    object Success: ScreenState()
}