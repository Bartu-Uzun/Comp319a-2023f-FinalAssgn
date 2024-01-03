package com.example.havanasiapp.data.local.repository

import com.example.havanasiapp.data.local.CityDao
import com.example.havanasiapp.domain.model.City

class CityRepositoryImpl (
    private val dao: CityDao
): ICityRepository {
    override suspend fun insertCity(city: City) {
        dao.insertCity(city)
    }

    override suspend fun deleteCity(city: City) {
        dao.deleteCity(city)
    }

    override fun getAllCities(): List<City> {
        return dao.getAllCities()
    }
}