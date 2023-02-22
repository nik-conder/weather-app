package com.example.weatherapp.di

import com.example.weatherapp.data.ApiRepository
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.useCase.WeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DomainModule {

    @Singleton
    @Provides
    fun provideWeatherUseCase(apiRepository: ApiRepository): WeatherUseCase {
        return WeatherUseCase(apiRepository)
    }
}