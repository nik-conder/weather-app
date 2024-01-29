package com.example.weatherapp.domain.repository

import com.example.weatherapp.data.enities.currentWeather.CurrentWeather
import com.example.weatherapp.data.enities.forecast.ForecastWeather
import retrofit2.Call

interface WeatherRepository {
    suspend fun getCurrentWeather(q: String, lang: String): Call<CurrentWeather>
    suspend fun getForecastWeather(q: String, lang: String, days: Int): Call<ForecastWeather>
    suspend fun getConnect(): Call<Int>
}