package com.example.weatherapp.data.repo

import com.example.weatherapp.data.network.WeatherSource
import com.example.weatherapp.data.enities.currentWeather.CurrentWeather
import com.example.weatherapp.data.enities.forecast.ForecastWeather
import com.example.weatherapp.domain.repository.WeatherRepository
import retrofit2.Call
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherSource: WeatherSource
    ): WeatherRepository {

    override suspend fun getCurrentWeather(q: String, lang: String): Call<CurrentWeather> {
        return weatherSource.getCurrentWeather(q = q, lang = lang)
    }

    override suspend fun getForecastWeather(q: String, lang: String, days: Int): Call<ForecastWeather> {
        return weatherSource.getForecastWeather(q = q, lang = lang, days = days)
    }

    override suspend fun getConnect(): Call<Int> {
        return weatherSource.getConnect()
    }
}