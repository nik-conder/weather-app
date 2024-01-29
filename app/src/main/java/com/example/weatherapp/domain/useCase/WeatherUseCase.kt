package com.example.weatherapp.domain.useCase

import com.example.weatherapp.data.repo.ApiRepository
import com.example.weatherapp.data.enities.currentWeather.CurrentWeather
import com.example.weatherapp.data.enities.WeatherStatus
import com.example.weatherapp.data.enities.forecast.ForecastWeather
import retrofit2.Call
import javax.inject.Inject

class WeatherUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    fun getForecastWeather(q: String, lang: String, days: Int): Call<ForecastWeather> {
        return apiRepository.getForecastWeather(q, lang, days)
    }
}