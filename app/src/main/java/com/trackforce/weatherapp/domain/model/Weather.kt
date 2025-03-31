package com.trackforce.weatherapp.domain.model

import com.trackforce.weatherapp.data.remote.response.WeatherResponse
import com.trackforce.weatherapp.data.remote.response.CurrentWeatherResponse
import com.trackforce.weatherapp.data.remote.response.WeatherInfoResponse

data class Weather(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezoneOffset: Int,
    val current: Current
) {
    fun toDomain(): WeatherResponse {
        return WeatherResponse(
            lat = lat,
            lon = lon,
            timezone = timezone,
            timezoneOffset = timezoneOffset,
            current = current.toDomain()
        )
    }
}

data class Current(
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: Double,
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    val dewPoint: Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Int,
    val windSpeed: Double,
    val windDeg: Int,
    val weather: List<WeatherInfo>
) {
    fun toDomain () : CurrentWeatherResponse {
        return CurrentWeatherResponse(
            dt = dt,
            sunrise = sunrise,
            sunset = sunset,
            temp = temp,
            feelsLike = feelsLike,
            pressure = pressure,
            humidity = humidity,
            dewPoint = dewPoint,
            uvi = uvi,
            clouds = clouds,
            visibility = visibility,
            windSpeed = windSpeed,
            windDeg = windDeg,
            weather = weather.map{it.toDomain()}
        )
    }
}

data class WeatherInfo (
    val id: Int,
    val main: String,
    val description: String,
    val icon: String,
) {
    fun toDomain() : WeatherInfoResponse {
        return WeatherInfoResponse(
            id = id,
            main = main,
            description = description,
            icon = icon
        )
    }
}