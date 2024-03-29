package com.example.weatherapp.di

import android.content.Context
import com.example.weatherapp.domain.useCase.NetworkUseCase
import com.example.weatherapp.domain.useCase.WeatherUseCase
import com.example.weatherapp.view.viewModel.HomeViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class PresentationModule {
    @Singleton
    @Provides
    fun provideMainViewModel(
        @ApplicationContext context: Context,
        weatherUseCase: WeatherUseCase,
        networkUseCase: NetworkUseCase
    ): HomeViewModel {
        return HomeViewModel(
            context,
            weatherUseCase,
            networkUseCase
        )
    }
}