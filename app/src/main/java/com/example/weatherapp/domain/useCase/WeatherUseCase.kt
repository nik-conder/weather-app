package com.example.weatherapp.domain.useCase

import com.example.weatherapp.data.ApiRepository
import com.example.weatherapp.data.test.CurrentWeather
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject


class WeatherUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    suspend fun getConnect(): Call<Int> {
        return apiRepository.getConnect()
    }
    suspend fun getWeather(q: String = "Moscow", lang: String = "ru"): Response<CurrentWeather> {
        return apiRepository.getWeather(q, lang).execute()
    }
}