package com.example.weatherapp.domain.repository

import com.example.weatherapp.data.test.CurrentWeather
import retrofit2.Call

interface WeatherRepository {
    suspend fun getWeather(): Call<CurrentWeather>
    suspend fun getConnect(): Call<Int>
}