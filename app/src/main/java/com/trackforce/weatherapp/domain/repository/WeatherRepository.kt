package com.trackforce.weatherapp.domain.repository

import com.trackforce.weatherapp.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeather(long: Double, lat: Double): Weather
}
