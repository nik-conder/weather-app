package com.example.weatherapp.view.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.data.currentWeather.CurrentWeather

@Composable
fun BlockInfoTemperature(
    width: Dp,
    currentWeather: CurrentWeather?
) {

    BlockInfo(width) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(0.9f),
        ) {
            Row() {
                Headline(text = "Подробная информация")
            }
            if (currentWeather != null) {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    currentWeather.current.let {
                        item {
                            Row() {
                                Text(
                                    fontSize = 14.sp,
                                    text = "Ветер: ${it.wind_kph} км/ч, ${it.wind_dir} направления"
                                )
                            }
                        }

                        item {
                            Row() {
                                Text(
                                    fontSize = 14.sp,
                                    text = "Атм. давление: ${it.pressure_mb} миллибар"
                                )//todo pressure_mb / 1.33 = мм рт.ст
                            }
                        }

                        item {
                            Row() {
                                Text(fontSize = 14.sp, text = "Влажность: ${it.humidity}%")
                            }
                        }

                        item {

                            Row() {
                                Text(fontSize = 14.sp, text = "Видимость: ${it.vis_km} км.")
                            }
                        }

                        item {
                            Row() {
                                Text(fontSize = 14.sp, text = "Прогноз от: ${it.last_updated}")
                            }
                        }
                    }
                }
            } else {
                Text(text = "Нет данных")
            }
        }
    }
}