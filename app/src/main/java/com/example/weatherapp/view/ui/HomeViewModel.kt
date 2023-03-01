package com.example.weatherapp.view.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.currentWeather.CurrentWeather
import com.example.weatherapp.data.forecast.ForecastWeather
import com.example.weatherapp.domain.useCase.NetworkUseCase
import com.example.weatherapp.domain.useCase.WeatherUseCase
import com.example.weatherapp.view.WeatherEvents
import com.example.weatherapp.view.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val context: Context,
    private val weatherUseCase: WeatherUseCase,
    private val networkUseCase: NetworkUseCase
) : ViewModel() {

    private val _stateWeather = MutableStateFlow(WeatherState())
    val stateWeather get() = _stateWeather

    init {
        viewModelScope.launch {
            val networkAvailable = networkUseCase.checkNetwork()
            _stateWeather.update { newValue -> newValue.copy(networkAvailable = networkAvailable) }
            loading()
        }
    }

    private fun loading() = CoroutineScope(Dispatchers.IO).launch {

        loadingCurrentWeather()
        loadingForecast()

        if (stateWeather.value.networkAvailable) {
            if (!stateWeather.value.isLoading)  _stateWeather.update { newValue -> newValue.copy(isLoading = true) }

        } else {
            Log.e("Network", "not available!")
        }
        loadingForecast()
        _stateWeather.update { newValue -> newValue.copy(isLoading = false) }
    }

    private fun loadingCurrentWeather() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val weatherLoad: Deferred<Response<CurrentWeather>> = async { weatherUseCase.getCurrentWeather(q = stateWeather.value.city, lang = stateWeather.value.lang) }
            println(weatherLoad.await().errorBody())
            val code = weatherLoad.await().code()
            val body = weatherLoad.await().body()

            if (code == 200) {
                _stateWeather.update { newValues -> newValues.copy(currentWeather = body)}
            } else {
                _stateWeather.update { newValue -> newValue.copy(isLoading = false) }
            }

        } catch (e: Exception) {
            println(e)
        }
    }
    private fun loadingForecast() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val weatherLoad: Deferred<Response<ForecastWeather>> = async { weatherUseCase.getForecastWeather(q = stateWeather.value.city, lang = stateWeather.value.lang, days = stateWeather.value.days) }
            println(weatherLoad.await().errorBody())
            val code = weatherLoad.await().code()
            val body = weatherLoad.await().body()

            if (code == 200) {
                _stateWeather.update { newValues -> newValues.copy(forecastWeather = body)}
            } else {
                _stateWeather.update { newValue -> newValue.copy(isLoading = false) }
            }
        } catch (e: Exception) {
            println(e)
        }
    }

    fun onEvent(event: WeatherEvents) {
        when (event) {
            WeatherEvents.Update -> {
                //loading()
            }
        }
    }
}