package com.example.weatherapp.view.state

import com.example.weatherapp.data.enities.forecast.Current
import com.example.weatherapp.data.enities.forecast.Forecast
import com.example.weatherapp.data.enities.forecast.Location
import com.example.weatherapp.view.ui.components.NotifyUI

data class WeatherState(
    val currentWeather: Current? = null,
    val forecastWeather: Forecast? = null,
    val location: Location? = null,
    val isLoading: Boolean = true,
    val networkAvailable: Boolean = false,
    val lang: String = "ru",
    val city: String = "Moscow",
    val days: Int = 5,
    val notificationsList: MutableList<NotifyUI.Notify>? = mutableListOf()
)