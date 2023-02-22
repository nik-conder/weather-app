package com.example.weatherapp.di

import com.example.weatherapp.data.ApiRepository
import com.example.weatherapp.data.WeatherRepositoryImpl
import com.example.weatherapp.data.network.WeatherSource
import com.example.weatherapp.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    private val baseURL = "http://api.weatherapi.com/"

    @Singleton
    @Provides
    fun provideRetrofit(): WeatherSource {
        return Retrofit.Builder().baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherSource::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(weatherSource: WeatherSource): WeatherRepository {
        return WeatherRepositoryImpl(weatherSource)
    }

    @Singleton
    @Provides
    fun provideApiRepository(weatherSource: WeatherSource): ApiRepository {
        return ApiRepository(weatherSource)
    }
}