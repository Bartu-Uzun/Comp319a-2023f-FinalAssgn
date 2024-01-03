package com.example.havanasiapp.data.local.repository

import com.example.havanasiapp.domain.model.City

interface ICityRepository {

    suspend fun insertCity(city: City)


    suspend fun deleteCity(city: City)


    fun getAllCities(): List<City>
}