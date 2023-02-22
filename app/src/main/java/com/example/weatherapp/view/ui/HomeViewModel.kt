package com.example.weatherapp.view.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.useCase.WeatherUseCase
import com.example.weatherapp.view.WeatherEvents
import com.example.weatherapp.view.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val context: Context,
    private val weatherUseCase: WeatherUseCase
) : ViewModel() {

    private val _stateWeather = MutableStateFlow(WeatherState())
    val stateWeather get() = _stateWeather

    fun onEvent(event: WeatherEvents) {
        when (event) {

            WeatherEvents.Update -> {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val weather = weatherUseCase.getWeather().body()
                        _stateWeather.update { newValues -> newValues.copy(currentWeather = weather)}
                        println("CONNECT STATUS: $weather")
                    } catch (e: Exception) {
                        println(e)
                    }

                    //_stateWeather.update { newValues -> newValues.copy(currentWeather = weather) }
                }
            }
        }
    }
}