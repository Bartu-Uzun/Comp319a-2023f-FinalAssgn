package com.example.havanasiapp.di

import android.app.Application
import androidx.room.Room
import com.example.havanasiapp.data.local.CityDatabase
import com.example.havanasiapp.data.local.repository.CityRepositoryImpl
import com.example.havanasiapp.data.local.repository.ICityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCityDatabase(app: Application): CityDatabase {

        return Room.databaseBuilder(
            app,
            CityDatabase::class.java,
            CityDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideCityRepository(db: CityDatabase): ICityRepository {

        return CityRepositoryImpl(db.cityDao)
    }

}


