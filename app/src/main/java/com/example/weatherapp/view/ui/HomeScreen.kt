package com.example.weatherapp.ui.theme

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.view.WeatherEvents
import com.example.weatherapp.view.ui.HomeViewModel
import com.example.weatherapp.view.ui.components.BlockInfoTemperature
import com.example.weatherapp.view.ui.components.BlockWeatherOnSeveralDays
import kotlinx.coroutines.delay
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    vm: HomeViewModel = viewModel()
) {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val stateWeather = vm.stateWeather.collectAsState()
    val onEvent = vm::onEvent
    val currentWeather = stateWeather.value.currentWeather
    val forecastWeather = stateWeather.value.forecastWeather?.forecast?.forecastday
    val networkAvailable = stateWeather.value.networkAvailable
    val isDay = if (currentWeather != null) currentWeather.current.is_day else 0
    val isLoading  = stateWeather.value.isLoading
    val colorList = if (isDay == 1) {
        listOf(
            Color(red = 0, green = 87, blue = 189, alpha = 255),
            Color(red = 90, green = 205, blue = 241, alpha = 255)
        )
    } else {
        listOf(
            Color(red = 4, green = 0, blue = 55, alpha = 255),
            Color(red = 4, green = 46, blue = 152, alpha = 255)
        )
    }

    val backgroundGradient = Brush.linearGradient(
        colors = colorList,
        start = Offset(0f, 0f),
        end = Offset(0f, screenHeight.value * 2)
    )

    val marginPage = 15.dp

    val sdf = SimpleDateFormat("dd.mm.yyyy hh:mm:ss")
    val currentDate = remember { mutableStateOf("") }


    LaunchedEffect(true)  {
        while (true) {
            currentDate.value = sdf.format(Date())
            delay(1000)
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .background(backgroundGradient)
            .fillMaxSize()
    ) {

        val (currentWeatherInfo, currentWeatherDetailBlock, weatherOn5DaysBlock) = createRefs()

        BoxWithConstraints(
            modifier = Modifier.constrainAs(currentWeatherInfo) {
                start.linkTo(parent.start, margin = marginPage)
                top.linkTo(parent.top, margin = marginPage)
                end.linkTo(parent.end, margin = marginPage)
            }
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                    Text(
                        text = currentDate.value,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }

                if (!networkAvailable) {
                    Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                        Text(
                            text = "Нет интерент соединения!",
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                    Row() {
                        Button(onClick = {
                            onEvent(WeatherEvents.Refresh)
                        }) {
                            Text(text = "Refresh")
                        }
                    }
                }

                if (isLoading) {
                    Row(
                        modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column() {
                            Text(text = "Загрузка...", fontSize = 20.sp)
                        }
                        Column() {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White)
                        }
                    }
                } else {
                    if (currentWeather != null) {
                        Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                            Text(
                                text = "${currentWeather.location.country}, ${currentWeather.location.region}",
                                fontSize = 12.sp,
                                color = Color.White
                            )
                        }
                    }
                    if (currentWeather != null) {
                        Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                            Text(
                                text = "${currentWeather.current.temp_c} °C",
                                fontSize = 42.sp,
                                color = Color.White,
                                fontWeight = FontWeight(600)
                            )
                        }
                    }
                    if (currentWeather != null) {
                        Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                            Text(
                                text = currentWeather.current.condition.text,
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
        BoxWithConstraints(
            modifier = Modifier.constrainAs(currentWeatherDetailBlock) {
                start.linkTo(parent.start, margin = marginPage)
                top.linkTo(currentWeatherInfo.bottom, margin = marginPage)
                end.linkTo(parent.end, margin = marginPage)
            }
        ) {
            BlockInfoTemperature(width = this.maxWidth, currentWeather = currentWeather)
        }

        BoxWithConstraints(
            modifier = Modifier.constrainAs(weatherOn5DaysBlock) {
                start.linkTo(parent.start, margin = marginPage)
                top.linkTo(currentWeatherDetailBlock.bottom, margin = marginPage)
                end.linkTo(parent.end, margin = marginPage)
            }
        ) {
            BlockWeatherOnSeveralDays(width = this.maxWidth, forecastWeather = forecastWeather)
        }
    }
}