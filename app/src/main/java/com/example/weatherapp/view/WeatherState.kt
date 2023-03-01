package com.example.weatherapp.view

import com.example.weatherapp.data.currentWeather.CurrentWeather
import com.example.weatherapp.data.forecast.ForecastWeather

data class WeatherState(
    val currentWeather: CurrentWeather? = null,
    val forecastWeather: ForecastWeather? = null,
    val isLoading: Boolean = true,
    val networkAvailable: Boolean = false,
    val lang: String = "ru",
    val city: String = "Pskov",
    val days: Int = 5
)