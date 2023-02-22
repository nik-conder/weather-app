package com.example.weatherapp.view.ui

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.test.CurrentWeather
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
    private val weatherUseCase: WeatherUseCase
) : ViewModel() {

    private val _stateWeather = MutableStateFlow(WeatherState())
    val stateWeather get() = _stateWeather

    init {
        viewModelScope.launch {
            loading()
        }
    }

    private fun loading() = CoroutineScope(Dispatchers.IO).launch {

       if (!stateWeather.value.isLoading)  _stateWeather.update { newValue -> newValue.copy(isLoading = true) }

        try {
            val weatherLoad: Deferred<Response<CurrentWeather>> = async { weatherUseCase.getWeather() }
            println(weatherLoad.await().errorBody())
            val code = weatherLoad.await().code()
            val body = weatherLoad.await().body()

            println(code)
            println(body)

            if (code == 200) {
                _stateWeather.update { newValues -> newValues.copy(currentWeather = body)}
            } else {
                _stateWeather.update { newValue -> newValue.copy(isLoading = false) }
            }

        } catch (e: Exception) {
            println(e)
        }
        _stateWeather.update { newValue -> newValue.copy(isLoading = false) }
    }

    fun onEvent(event: WeatherEvents) {
        when (event) {
            WeatherEvents.Update -> {
                //loading()
            }
        }
    }
}