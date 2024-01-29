package com.example.weatherapp.data.enities

sealed class WeatherStatus() {
    data object Success : WeatherStatus()
    data object Error: WeatherStatus()
}
