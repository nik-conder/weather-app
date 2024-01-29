package com.example.weatherapp.view.event

sealed class WeatherEvents {
    data object Update: WeatherEvents()
    data object Refresh: WeatherEvents()
}