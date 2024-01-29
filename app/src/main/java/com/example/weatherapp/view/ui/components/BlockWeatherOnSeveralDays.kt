package com.example.weatherapp.view.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.weatherapp.data.enities.forecast.Forecast

@Composable
fun BlockWeatherOnSeveralDays(
    width: Dp,
    forecastWeather: Forecast?
) {
    BlockInfo(width) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(0.9f)
        ) {
            Row {
                Headline(text = "Прогноз погоды на 5 дней")
            }
            if (forecastWeather != null) {
                LazyColumn(
                    modifier = Modifier
                        .heightIn(min = 50.dp, max = 500.dp)
                        .wrapContentHeight(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    userScrollEnabled = true
                ) {
                    items(forecastWeather.forecastday) { item ->
                        ItemForecastWeather(forecastDay = item)
                    }
                }
            } else {
                Row {
                    Text(text = "Нет данных")
                }
            }
        }
    }
}