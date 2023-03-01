package com.example.weatherapp.domain.useCase

import com.example.weatherapp.data.ApiRepository
import com.example.weatherapp.data.currentWeather.CurrentWeather
import com.example.weatherapp.data.forecast.ForecastWeather
import retrofit2.Response
import javax.inject.Inject


class WeatherUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    suspend fun getCurrentWeather(q: String, lang: String): Response<CurrentWeather> {
        return apiRepository.getCurrentWeather(q, lang).execute()
    }

    suspend fun getForecastWeather(q: String, lang: String, days: Int): Response<ForecastWeather> {
        return apiRepository.getForecastWeather(q, lang, days).execute()
    }
}