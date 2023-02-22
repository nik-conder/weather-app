package com.example.weatherapp.data.network

import com.example.weatherapp.data.test.CurrentWeather
import retrofit2.Call
import retrofit2.http.GET

interface WeatherSource {

    @GET("/v1/current.json?key=264bba121a834c6daf253227232202&q=London&aqi=no")
    fun getWeather(): Call<CurrentWeather>

    @GET("/")
    fun getConnect(): Call<Int>

}