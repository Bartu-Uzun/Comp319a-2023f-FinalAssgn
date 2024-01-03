package com.example.havanasiapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.havanasiapp.domain.model.City

@Database(
    entities = [City::class],
    version = 1
)
abstract class CityDatabase: RoomDatabase() {

    abstract val cityDao: CityDao

    // name of our database
    companion object {
        const val DATABASE_NAME = "city_db"
    }

}