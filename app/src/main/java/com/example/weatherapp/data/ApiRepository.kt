package com.example.weatherapp.data

import com.example.weatherapp.data.network.WeatherSource
import com.example.weatherapp.data.currentWeather.CurrentWeather
import com.example.weatherapp.data.forecast.ForecastWeather
import retrofit2.Call
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val weatherSource: WeatherSource
) {
    fun getCurrentWeather(q: String, lang: String): Call<CurrentWeather> {
        return weatherSource.getCurrentWeather(q = q, lang = lang)
    }

    fun getForecastWeather(q: String, lang: String, days: Int): Call<ForecastWeather> {
        return weatherSource.getForecastWeather(q = q, lang = lang, days = days)
    }
}