package com.example.weatherapp.domain.useCase

import com.example.weatherapp.data.ApiRepository
import com.example.weatherapp.data.currentWeather.CurrentWeather
import com.example.weatherapp.data.forecast.ForecastWeather
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.awaitResponse
import javax.inject.Inject

sealed class WeatherStatus() {
    class Success: WeatherStatus()
    object Error: WeatherStatus()
}

data class Weather(
    val status: WeatherStatus,
    val currentWeather: CurrentWeather,
    val forecastWeather: ForecastWeather
)

class WeatherUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

//    suspend fun getCurrentWeather(q: String, lang: String): Call<CurrentWeather> {
//        return apiRepository.getCurrentWeather(q, lang)
//    }

    suspend fun getForecastWeather(q: String, lang: String, days: Int): Call<ForecastWeather> {
        return apiRepository.getForecastWeather(q, lang, days)
    }
}