package com.example.weatherapp.di

import android.content.Context
import com.example.weatherapp.data.repo.ApiRepository
import com.example.weatherapp.domain.useCase.NetworkUseCase
import com.example.weatherapp.domain.useCase.WeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Singleton
    @Provides
    fun provideNetworkUseCase(@ApplicationContext context: Context) : NetworkUseCase {
        return NetworkUseCase(context)
    }
}