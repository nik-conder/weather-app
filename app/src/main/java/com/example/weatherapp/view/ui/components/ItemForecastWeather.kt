package com.example.weatherapp.view.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherapp.data.forecast.Forecastday

@Composable
fun ItemForecastWeather(
    forecastDay: Forecastday
) {

    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 6.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.5f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = forecastDay.date)
        }
        Column(
            modifier = Modifier.fillMaxWidth(1f),
            horizontalAlignment = Alignment.End
        ) {
            Text(text = "${forecastDay.day.maxtemp_c}°C | ${forecastDay.day.mintemp_c} °C")
        }
    }
}