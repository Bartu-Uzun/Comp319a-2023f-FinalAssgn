package com.example.havanasiapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.havanasiapp.domain.model.City

@Dao
interface CityDao {

    @Insert
    suspend fun insertCity(city: City)

    @Delete
    suspend fun deleteCity(city: City)

    @Query("SELECT * FROM city")
    fun getAllCities(): List<City>

}