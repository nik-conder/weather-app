package com.example.weatherapp.data.forecast

data class ForecastWeather(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)