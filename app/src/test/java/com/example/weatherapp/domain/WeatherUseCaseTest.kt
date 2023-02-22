package com.example.weatherapp.domain

import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.domain.useCase.WeatherUseCase

import org.junit.After
import org.junit.Before
import org.junit.Test

class WeatherUseCaseTest {

    val useCase: WeatherUseCase = WeatherUseCase(weatherRepository = WeatherRepository())

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `get weather`() {
        val actual: Map<String, Int> = useCase.getWeather()
        val expect = mapOf<String, Int>(
            "wind" to 10,
            "atmospheric_pressure" to 700,
            "humidity" to 80,
            "visibility" to 10,
            "updated" to 0
        )
    }
}