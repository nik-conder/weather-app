package com.example.weatherapp.ui.theme

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.data.test.CurrentWeather
import com.example.weatherapp.view.WeatherEvents
import com.example.weatherapp.view.ui.HomeViewModel

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

    val colorList = listOf(
        Color(red = 32, green = 124, blue = 223, alpha = 255),
        Color(red = 32, green = 124, blue = 223, alpha = 150)
    )

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

        var (date, temperature, currentWeatherDetailInfo, weatherOn10Days, btnRefresh) = createRefs()

        BoxWithConstraints(
            modifier = Modifier.constrainAs(date) {
                start.linkTo(parent.start, margin = marginPage)
                top.linkTo(parent.top, margin = marginPage)
                end.linkTo(parent.end, margin = marginPage)
            }
        ) {
            Row() {
                Text(
                    text = "Сегодня 18 февраля, суббота, 11:21",
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
        }

        BoxWithConstraints(
            modifier = Modifier.constrainAs(temperature) {
                start.linkTo(parent.start, margin = marginPage)
                top.linkTo(date.bottom, margin = marginPage)
                end.linkTo(parent.end, margin = marginPage)
            }
        ) {
            Row() {
                Text(
                    text = "+ 32° C",
                    fontSize = 32.sp,
                    color = Color.White
                )
            }
        }

        BoxWithConstraints(
            modifier = Modifier.constrainAs(currentWeatherDetailInfo) {
                start.linkTo(parent.start, margin = marginPage)
                top.linkTo(temperature.bottom, margin = marginPage)
                end.linkTo(parent.end, margin = marginPage)
            }
        ) {
            BlockInfoTemperature(width = this.maxWidth, currentWeather = currentWeather)
        }

        BoxWithConstraints(
            modifier = Modifier.constrainAs(weatherOn10Days) {
                start.linkTo(parent.start, margin = marginPage)
                top.linkTo(currentWeatherDetailInfo.bottom, margin = marginPage)
                end.linkTo(parent.end, margin = marginPage)
            }
        ) {
            BlockWeatherOnSeveralDays(width = this.maxWidth)
        }

        BoxWithConstraints(
            modifier = Modifier.constrainAs(btnRefresh) {
                top.linkTo(weatherOn10Days.bottom)
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
                .padding(bottom = 10.dp)
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
                fontSize = 14.sp,
                color = Color.White
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
                Header(text = "Детальная информация")
            }
            if (currentWeather != null) {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    currentWeather.current.let {
                        item {
                            Row() {
                                Text(color = Color.White, fontSize = 14.sp, text = "Обновлено: ${it.last_updated}")
                            }
                            Row() {
                                Text(color = Color.White, fontSize = 14.sp, text = "Ветер: ${it.wind_kph} ${it.wind_dir}")
                            }
                            Row() {
                                Text(color = Color.White, fontSize = 14.sp, text = "Видимость: ${it.vis_km} км")
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
                Header(text = "Погода на 5 дней")
            }
            Row {
                Text(
                    text = "123"
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