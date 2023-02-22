package com.example.weatherapp.view

sealed class WeatherEvents {
    object Update: WeatherEvents()
}