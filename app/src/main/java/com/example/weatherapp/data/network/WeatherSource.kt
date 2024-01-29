package com.example.weatherapp.data.network

import com.example.weatherapp.data.enities.currentWeather.CurrentWeather
import com.example.weatherapp.data.enities.forecast.ForecastWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherSource {
    @Headers("Accept: application/json")
    @GET("/v1/current.json")
    fun getCurrentWeather(
        @Query("key") key: String = KEY_API,
        @Query("q") q: String,
        @Query("lang") lang: String
    ): Call<CurrentWeather>

    @Headers("Accept: application/json")
    @GET("/v1/forecast.json")
    fun getForecastWeather(
        @Query("key") key: String = KEY_API,
        @Query("q") q: String,
        @Query("days") days: Int,
        @Query("lang") lang: String
    ): Call<ForecastWeather>

    @GET("/")
    fun getConnect(): Call<Int>

    companion object {
        const val KEY_API: String = "264bba121a834c6daf253227232202"
    }

}