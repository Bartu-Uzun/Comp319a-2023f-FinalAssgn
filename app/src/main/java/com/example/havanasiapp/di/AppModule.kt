package com.example.havanasiapp.di

import android.app.Application
import androidx.room.Room
import com.example.havanasiapp.data.local.CityDatabase
import com.example.havanasiapp.data.local.repository.CityRepositoryImpl
import com.example.havanasiapp.data.local.repository.ICityRepository
import com.example.havanasiapp.data.remote.WeatherApiService
import com.example.havanasiapp.data.remote.repository.IWeatherRepository
import com.example.havanasiapp.data.remote.repository.WeatherRepositoryImpl
import com.example.havanasiapp.util.Constants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
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

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApiService {

        val clientInterceptor = Interceptor { chain ->
            var request: Request = chain.request()
            val url: HttpUrl = request.url.newBuilder()
                .addQueryParameter("key", Constants.API_KEY)
                .build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }

        val client = OkHttpClient.Builder().addInterceptor(clientInterceptor).build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(Json.asConverterFactory(Constants.contentType))
            .client(client)
            .build()
            .create(WeatherApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherApiService): IWeatherRepository {

        return WeatherRepositoryImpl(api)
    }










}


