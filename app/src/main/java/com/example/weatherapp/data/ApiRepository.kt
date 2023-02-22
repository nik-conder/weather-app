package com.example.weatherapp.data

import com.example.weatherapp.data.network.WeatherSource
import com.example.weatherapp.data.test.CurrentWeather
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val weatherSource: WeatherSource
) {
    fun getWeather(q: String, lang: String): Call<CurrentWeather> {
        return weatherSource.getWeather(q = q, lang = lang)
    }
    fun getConnect(): Call<Int> {
        return weatherSource.getConnect()
    }
}