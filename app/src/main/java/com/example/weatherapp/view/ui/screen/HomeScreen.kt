package com.example.weatherapp.ui.theme

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.view.event.WeatherEvents
import com.example.weatherapp.view.viewModel.HomeViewModel
import com.example.weatherapp.view.event.SearchEvents
import com.example.weatherapp.view.ui.components.BlockInfoTemperature
import com.example.weatherapp.view.ui.components.BlockWeatherOnSeveralDays
import kotlinx.coroutines.delay
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
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
    val onEventSearch = vm::onEventSearch
    //val onEventNotify = vm::onEventNotify
    val currentWeather = stateWeather.value.currentWeather
    val forecastWeather = stateWeather.value.forecastWeather
    val location = stateWeather.value.location
    val networkAvailable = stateWeather.value.networkAvailable
    //val notificationsList = stateWeather.value.notificationsList
    val isDay = if (currentWeather != null) currentWeather.is_day else 0
    val isLoading = stateWeather.value.isLoading
    val scrollVertical = rememberScrollState()
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

    val searchState = remember { mutableStateOf(false) }
    val textCity = remember { mutableStateOf(TextFieldValue("")) }
    //val showKeyboard = remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current
    val cityTextMaxChar = 24

    LaunchedEffect(true) {
        while (true) {
            currentDate.value = sdf.format(Date())
            delay(1000)
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground
                ),
                navigationIcon = {
                    AnimatedVisibility(visible = searchState.value) {
                        IconButton(onClick = {
                            searchState.value = !searchState.value
                        }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                        }
                    }
                },
                title = {
                    AnimatedVisibility(visible = !searchState.value) {
                        Text(text = "Weather App")
                    }
                    AnimatedVisibility(visible = searchState.value) {
                        LaunchedEffect(searchState.value) {
                            focusRequester.requestFocus()
                            keyboard?.show()
                        }
                        OutlinedTextField(
                            textStyle = MaterialTheme.typography.titleSmall,
                            value = textCity.value,
                            minLines = 1,
                            maxLines = 1,
                            singleLine = true,
                            //label = { Text(text = "Город") },
                            modifier = Modifier
                                .focusRequester(focusRequester)
                                .height(56.dp),
                            shape = RoundedCornerShape(20.dp),
                            isError = cityTextMaxChar - textCity.value.text.length == 0,
                            keyboardOptions = KeyboardOptions.Default,
                            onValueChange = { newText ->
                                if (newText.text.length <= cityTextMaxChar) textCity.value =
                                    newText
                            },
                            trailingIcon = {
                                AnimatedVisibility(visible = textCity.value.text.isNotEmpty()) {
                                    Icon(
                                        modifier = Modifier.clickable {
                                            textCity.value = TextFieldValue("")
                                        },
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = "Clear"
                                    )
                                }
                            },
                        )
                    }
                },
                actions = {
                    if (searchState.value) {
                        AnimatedVisibility(visible = textCity.value.text.isNotEmpty()) {
                            IconButton(onClick = {
                                onEventSearch(SearchEvents.Check(city = textCity.value.text))
                            }) {
                                Icon(imageVector = Icons.Default.Check, contentDescription = "OK")
                            }
                        }
                    } else {
                        IconButton(onClick = {
                            searchState.value = !searchState.value
                        }) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                        }
                    }
                })
        },
        bottomBar = {
           /* BottomAppBar() {
                Button(onClick = {
                    onEventNotify(
                        NotifyEvents.Add(
                            notify = NotifyUI.Notify(
                                id = 1,
                                title = "Hello world",
                                status = NotifyUI.NotifyStatus.Success
                            )
                        )
                    )
                }) {
                    Text(text = "Notify add")
                }
                Button(onClick = {
                    onEventNotify(NotifyEvents.Delete)
                }) {
                    Text(text = "Notify delete")
                }
            }*/
        }
    ) { paddingValues ->
        ConstraintLayout(
            modifier = Modifier
                .background(backgroundGradient)
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(scrollVertical)
        ) {

            val (currentWeatherInfo, currentWeatherDetailBlock, weatherOn5DaysBlock) = createRefs()

            /*if (notificationsList != null) {
                if (notificationsList.isNotEmpty()) {
                    Notify(state = true, notificationsList = notificationsList)
                }
            }*/

            Box(
                modifier = Modifier
                    .testTag("currentWeatherInfo")
                    .constrainAs(currentWeatherInfo) {
                        start.linkTo(parent.start, margin = marginPage)
                        top.linkTo(parent.top, margin = marginPage)
                        end.linkTo(parent.end, margin = marginPage)
                    }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                        Text(
                            text = currentDate.value,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }

                    if (!networkAvailable) {
                        Row(
                            modifier = Modifier
                                .padding(top = 8.dp, bottom = 8.dp)
                                .testTag("textNotNetwork")
                        ) {
                            Text(
                                text = "Нет интернет соединения!",
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }
                        Row {
                            Button(
                                modifier = Modifier.testTag("btnRefresh"),
                                onClick = {
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
                            Column {
                                Text(text = "Загрузка...", fontSize = 20.sp)
                            }
                            Column {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = Color.White
                                )
                            }
                        }
                    } else {
                        if (location != null) {
                            Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                                Text(
                                    text = "${location.country}, ${location.region}",
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                            }
                        }
                        if (currentWeather != null) {
                            Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                                Text(
                                    text = "${currentWeather.temp_c} °C",
                                    fontSize = 42.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight(600)
                                )
                            }
                        }
                        if (currentWeather != null) {
                            Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                                Text(
                                    text = currentWeather.condition.text,
                                    fontSize = 16.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .testTag("currentWeatherDetailBlock")
                    .constrainAs(currentWeatherDetailBlock) {
                        start.linkTo(parent.start, margin = marginPage)
                        top.linkTo(currentWeatherInfo.bottom, margin = marginPage)
                        end.linkTo(parent.end, margin = marginPage)
                    }
            ) {
                BlockInfoTemperature(width = screenWidth, currentWeather = currentWeather)
            }

            Box(
                modifier = Modifier
                    .testTag("weatherOn5DaysBlock")
                    .constrainAs(weatherOn5DaysBlock) {
                        start.linkTo(parent.start, margin = marginPage)
                        top.linkTo(currentWeatherDetailBlock.bottom, margin = marginPage)
                        end.linkTo(parent.end, margin = marginPage)
                    }
            ) {
                BlockWeatherOnSeveralDays(width = screenWidth, forecastWeather = forecastWeather)
            }
        }
    }
}