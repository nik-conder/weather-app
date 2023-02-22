package com.example.weatherapp.data.network

import com.example.weatherapp.Constants.API.KEY_API
import com.example.weatherapp.data.test.CurrentWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherSource {

    @GET("/v1/current.json")
    fun getWeather(
        @Query("key") key: String = KEY_API,
        @Query("q") q: String,
        @Query("lang") lang: String
    ): Call<CurrentWeather>

    @GET("/v1/forecast.json")
    fun getWeatherOn(@Query("key") key: String = KEY_API)

    @GET("/")
    fun getConnect(): Call<Int>

}