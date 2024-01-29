package com.example.weatherapp.data.repo

import com.example.weatherapp.data.network.WeatherSource
import com.example.weatherapp.data.enities.currentWeather.CurrentWeather
import com.example.weatherapp.data.enities.forecast.ForecastWeather
import retrofit2.Call
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val weatherSource: WeatherSource
) {
    fun getForecastWeather(q: String, lang: String, days: Int): Call<ForecastWeather> {
        return weatherSource.getForecastWeather(q = q, lang = lang, days = days)
    }
}