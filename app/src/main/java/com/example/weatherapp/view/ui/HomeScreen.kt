package com.example.weatherapp.ui.theme

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.data.test.CurrentWeather
import com.example.weatherapp.view.WeatherEvents
import com.example.weatherapp.view.ui.HomeViewModel
import kotlinx.coroutines.delay
import java.time.LocalDateTime

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

    ConstraintLayout(
        modifier = Modifier
            .background(backgroundGradient)
            .fillMaxSize()
    ) {

        var (currentWeatherInfo, currentWeatherDetailBlock, weatherOn5DaysBlock, btnRefresh) = createRefs()

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
                        text = "Сегодня 18 февраля, суббота, 11:21",
                        fontSize = 14.sp,
                        color = Color.White
                    )
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
            BlockWeatherOnSeveralDays(width = this.maxWidth)
        }

        BoxWithConstraints(
            modifier = Modifier.constrainAs(btnRefresh) {
                top.linkTo(weatherOn5DaysBlock.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            Button(onClick = {
                onEvent(WeatherEvents.Update)
            }) {
                Text(text = "Update")
            }
        }

    }
}

@Composable
fun Header(
    text: String
) {
    BoxWithConstraints {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .drawBehind {
                    drawLine(
                        Color.LightGray,
                        Offset(0f, 70f),
                        Offset(this.size.width, 70f),
                        1f
                    )
                }) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight(500)
            )
        }
    }

}

@Composable
fun BlockInfo(
    width: Dp,
    content: @Composable() (BoxScope.() -> Unit)
) {
    val colorList = listOf(
        Color(red = 24, green = 110, blue = 202, alpha = 100),
        Color(red = 62, green = 95, blue = 131, alpha = 155)
    )

    val backgroundGradient = Brush.linearGradient(
        colors = colorList,
        start = Offset(width.value * 2, 0f),
        end = Offset(0f, 0f)
    )

    Box(
        modifier = Modifier
            .background(backgroundGradient, RoundedCornerShape(20.dp)),
        content = content
    )
}

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
                Header(text = "Подробная информация")
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

@Composable
fun BlockWeatherOnSeveralDays(
    width: Dp
) {
    BlockInfo(width) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(0.9f),
        ) {
            Row() {
                Header(text = "Прогноз погоды на 5 дней")
            }
            Row {
                Text(
                    text = "Нет данных"
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}