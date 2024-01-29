package com.example.weatherapp.view.viewModel

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.enities.forecast.Current
import com.example.weatherapp.data.enities.forecast.Forecast
import com.example.weatherapp.data.enities.forecast.ForecastWeather
import com.example.weatherapp.data.enities.forecast.Location
import com.example.weatherapp.domain.useCase.NetworkUseCase
import com.example.weatherapp.domain.useCase.WeatherUseCase
import com.example.weatherapp.view.event.SearchEvents
import com.example.weatherapp.view.event.WeatherEvents
import com.example.weatherapp.view.state.WeatherState
import com.example.weatherapp.view.ui.components.NotifyUI
import com.example.weatherapp.view.ui.theme.NotifyEvents
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
            if (networkAvailable) {
                loading()
            } else {
                _stateWeather.update { newValue ->
                    newValue.copy(
                        isLoading = false,
                        networkAvailable = false
                    )
                }
                autoUpdate()
            }
        }
    }

    private fun autoUpdate() = CoroutineScope(Dispatchers.IO).launch {
        while (true) {
            val network = networkUseCase.checkNetwork()
            if (network) {
                this.cancel()
                loading()
            }
        }
    }

    private fun loading() = CoroutineScope(Dispatchers.IO).launch {
        delay(5000)
        if (stateWeather.value.networkAvailable) {
            if (!stateWeather.value.isLoading) _stateWeather.update { newValue ->
                newValue.copy(
                    isLoading = true
                )
            }
            loadingWeather(q = stateWeather.value.city)
        } else {
            Log.e("Network", "not available!")
        }
        _stateWeather.update { newValue -> newValue.copy(isLoading = false) }
    }

    private fun loadingWeather(q: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val weatherLoad: Deferred<Response<ForecastWeather>> = async {
                weatherUseCase.getForecastWeather(
                    q = q,
                    lang = stateWeather.value.lang,
                    days = stateWeather.value.days
                ).execute()
            }
            val code = weatherLoad.await().code()
            val body = weatherLoad.await().body()

            if (code == 200) {
                if (body != null) {
                    updateWeather(body)
                }
            } else {
                _stateWeather.update { newValue -> newValue.copy(isLoading = false) }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }
    }

    private fun updateWeather(forecastWeather: ForecastWeather?) {
        if (forecastWeather != null) {
            updateCurrentWeather(forecastWeather.current)
            updateForecastWeather(forecastWeather.forecast)
            updateLocation(forecastWeather.location)
        }
    }
    private fun updateCurrentWeather(current: Current?) {
        if (current != null) {
            _stateWeather.update { newValues -> newValues.copy(currentWeather = current) }
        }
    }

    private fun updateForecastWeather(forecast: Forecast?) {
        if (forecast != null) {
            _stateWeather.update { newValues -> newValues.copy(forecastWeather = forecast) }
        }
    }

    private fun updateLocation(location: Location?) {
        if (location != null) {
            _stateWeather.update { newValues -> newValues.copy(location = location) }
        }
    }

    fun onEvent(event: WeatherEvents) {
        when (event) {
            WeatherEvents.Update -> {
                //loading()
            }
            WeatherEvents.Refresh -> {
                val network = networkUseCase.checkNetwork()
                if (network) {
                    _stateWeather.update { newValue -> newValue.copy(networkAvailable = true) }
                    loading()
                } else {
                    Toast.makeText(context, "Нет соединения с интернетом!", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

   /* fun onEventNotify(event: NotifyEvents) {

        // TODO: остановились тут!!!!!!!

        when (event) {
            is NotifyEvents.Delete -> {
                val newList = stateWeather.value.notificationsList?.removeFirstOrNull()
                _stateWeather.update { newValues ->
                    newValues.copy(notificationsList = mutableListOf<NotifyUI.Notify>())
                }

            }
            is NotifyEvents.Add -> {
                val newList: MutableList<NotifyUI.Notify> = mutableListOf(event.notify)
                _stateWeather.update { newValues ->
                    newValues.copy(
                        notificationsList = newList)
                }
            }
        }
    }*/
    fun onEventSearch(event: SearchEvents) {
        when (event) {
            is SearchEvents.Check -> {
                val network = networkUseCase.checkNetwork()
                if (network) {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val request = async {
                                weatherUseCase.getForecastWeather(
                                    q = event.city,
                                    lang = stateWeather.value.lang,
                                    days = stateWeather.value.days
                                )
                            }

                            val execute = request.await().execute()

                            if (execute.code() == 200) {
                                if (execute.body() != null) {
                                    loadingWeather(q = execute.body()!!.location.region)
                                }
                            } else if (execute.code() == 400) {
                                println("Неизвестная ошибка")
                            } else {
                                println("Неизвестная ошибка")
                            }
                        } catch (e: Exception) {
                            Log.e("Search", e.message.toString())
                            println("неизвестная ошибка")
                        }
                    }
                } else {
                    Toast.makeText(context, "Нет соединения с интернетом!", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
}