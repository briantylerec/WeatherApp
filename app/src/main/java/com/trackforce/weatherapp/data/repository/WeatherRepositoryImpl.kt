package com.trackforce.weatherapp.data.repository

import android.content.Context
import com.trackforce.weatherapp.Utils.isNetworkAvailable
import com.trackforce.weatherapp.data.remote.WeatherApi
import com.trackforce.weatherapp.data.remote.response.WeatherResponse
import com.trackforce.weatherapp.domain.model.Weather
import com.trackforce.weatherapp.domain.repository.WeatherRepository
import com.trackforce.weatherapp.preferences.SecurePreferences
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val securePreferences: SecurePreferences,
    private val context: Context
) : WeatherRepository {

    override suspend fun getWeather(lat: Double, lon: Double): Weather {
        return if (isNetworkAvailable(context)) {
            val response = api.getWeatherByCoordinates(
                lat,
                lon,
                "metric",
                "hourly,daily,minutely",
                "WEATHER_API"
            )

            // Save the weather data to SharedPreferences
            response.body()?.let {
                securePreferences.saveObject("weather_data", it)
            }

            // Return the weather data from the API
            response.body()!!.toDomain()
        } else {
            // If no internet connection, get data from SharedPreferences
            val savedWeather = securePreferences.getObject("weather_data", WeatherResponse::class.java)

            // Throw exception if no data is available
            savedWeather?.let {
                return it.toDomain()
            } ?: throw Exception("No data available and no internet connection")
        }
    }
}