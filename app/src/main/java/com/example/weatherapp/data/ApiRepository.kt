package com.example.weatherapp.data

import com.example.weatherapp.data.network.WeatherSource
import com.example.weatherapp.data.test.CurrentWeather
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val weatherSource: WeatherSource
) {
    fun getWeather(): Response<CurrentWeather> {
        return weatherSource.getWeather().execute()
    }
    fun getConnect(): Call<Int> {
        return weatherSource.getConnect()
    }
}