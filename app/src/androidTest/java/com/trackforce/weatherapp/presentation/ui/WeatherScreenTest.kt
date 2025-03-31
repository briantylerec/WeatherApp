package com.trackforce.weatherapp.presentation.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.trackforce.weatherapp.domain.model.Current
import com.trackforce.weatherapp.domain.model.Weather
import com.trackforce.weatherapp.domain.model.WeatherInfo
import org.junit.Rule
import org.junit.Test

class WeatherScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    val weatherInfo = WeatherInfo(
        id = 1,
        main = "Clear",
        description = "Clear sky",
        icon = "01d"
    )

    val current = Current(
        dt = 1633036800L,
        sunrise = 1633010400L,
        sunset = 1633053600L,
        temp = 25.0,
        feelsLike = 23.5,
        pressure = 1013,
        humidity = 60,
        dewPoint = 17.0,
        uvi = 5.0,
        clouds = 10,
        visibility = 10000,
        windSpeed = 5.5,
        windDeg = 180,
        weather = listOf(weatherInfo)
    )

    val weather = Weather(
        lat = 37.7749,
        lon = -122.4194,
        timezone = "America/Los_Angeles",
        timezoneOffset = -7,
        current = current
    )

    @Test
    fun whenComponentStart_thenHumidytyInformationIsDisplayed() {
        composeTestRule.setContent { WeatherInformationBody(weather) }

        composeTestRule.onNodeWithText("HUMIDITY").assertExists()
    }

    @Test
    fun whenComponentStart_thenWeatherInformationBodyIsDisplayed() {
        composeTestRule.setContent { WeatherInformationBody(weather) }

        composeTestRule.onNodeWithTag("weatherInformationBody").assertIsDisplayed()
    }
}