package com.example.weatherapp.view.ui

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.weatherapp.MainActivity
import com.example.weatherapp.ui.theme.HomeScreen
import com.example.weatherapp.view.ui.theme.WeatherAppTheme
import org.junit.Rule
import org.junit.Test

class HomeScreenKtTest {


    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun myTest() {
        // Start the app
        composeTestRule.activity.setContent {
            WeatherAppTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                HomeScreen()
            }
        }
        }
    }

    @Test
    fun text_networking_available() {
        val textNotNetwork = composeTestRule.onNodeWithTag("textNotNetwork")
        textNotNetwork.assertExists()
        textNotNetwork.assertIsDisplayed()
    }

    @Test
    fun btn_refresh() {
        val btnRefresh = composeTestRule.onNodeWithTag("btnRefresh")
        btnRefresh.assertExists()
        btnRefresh.assertIsDisplayed()
        btnRefresh.assertIsEnabled()
        btnRefresh.assertIsNotFocused()
        btnRefresh.performClick()
    }

    @Test
    fun current_weather_info_block() {
        val currentWeatherInfo = composeTestRule.onNodeWithTag("currentWeatherInfo")
        currentWeatherInfo.assertExists()
        currentWeatherInfo.assertIsDisplayed()
    }

    @Test
    fun current_weather_detail_block() {
        val currentWeatherDetailBlock = composeTestRule.onNodeWithTag("currentWeatherDetailBlock")
        currentWeatherDetailBlock.assertExists()
        currentWeatherDetailBlock.assertIsDisplayed()
    }

    @Test
    fun weather_on_5_days_block() {
        val currentWeatherInfo = composeTestRule.onNodeWithTag("weatherOn5DaysBlock")
        currentWeatherInfo.assertExists()
        currentWeatherInfo.assertIsDisplayed()
    }
}