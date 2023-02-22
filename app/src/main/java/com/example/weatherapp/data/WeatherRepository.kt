package com.example.weatherapp.data

import com.example.weatherapp.data.network.WeatherSource
import com.example.weatherapp.data.test.CurrentWeather
import com.example.weatherapp.domain.repository.WeatherRepository
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherSource: WeatherSource
    ): WeatherRepository {

    override suspend fun getWeather(): Call<CurrentWeather> {
        return weatherSource.getWeather()
    }

    override suspend fun getConnect(): Call<Int> {
        return weatherSource.getConnect()
    }
}