package com.example.weatherapp.view

import com.example.weatherapp.data.forecast.Current
import com.example.weatherapp.data.forecast.Forecast
import com.example.weatherapp.data.forecast.Location
import com.example.weatherapp.view.ui.components.NotifyUI

data class WeatherState(
    val currentWeather: Current? = null,
    val forecastWeather: Forecast? = null,
    val location: Location? = null,
    val isLoading: Boolean = true,
    val networkAvailable: Boolean = false,
    val lang: String = "ru",
    val city: String = "Pskov",
    val days: Int = 5,
    val notificationsList: MutableList<NotifyUI.Notify>? = mutableListOf()
)