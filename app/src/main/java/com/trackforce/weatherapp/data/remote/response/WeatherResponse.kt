package com.trackforce.weatherapp.data.remote.response

import com.google.gson.annotations.SerializedName
import com.trackforce.weatherapp.domain.model.Current
import com.trackforce.weatherapp.domain.model.Weather
import com.trackforce.weatherapp.domain.model.WeatherInfo

data class WeatherResponse(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double,
    @SerializedName("timezone") val timezone: String,
    @SerializedName("timezone_offset") val timezoneOffset: Int,
    @SerializedName("current") val current: CurrentWeatherResponse
) {
    fun toDomain(): Weather {
        return Weather(
            lat = lat,
            lon = lon,
            timezone = timezone,
            timezoneOffset = timezoneOffset,
            current = current.toDomain()
        )
    }
}

data class CurrentWeatherResponse (
    @SerializedName("dt") val dt: Long,
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long,
    @SerializedName("temp") val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("dew_point") val dewPoint: Double,
    @SerializedName("uvi") val uvi: Double,
    @SerializedName("clouds") val clouds: Int,
    @SerializedName("visibility") val visibility: Int,
    @SerializedName("wind_speed") val windSpeed: Double,
    @SerializedName("wind_deg") val windDeg: Int,
    @SerializedName("weather") val weather: List<WeatherInfoResponse>,
) {
    fun toDomain () : Current {
        return Current(
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
            weather = weather.map { it.toDomain()}
        )
    }
}

data class WeatherInfoResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String,
) {
    fun toDomain() : WeatherInfo {
        return WeatherInfo(
            id = id,
            main = main,
            description = description,
            icon = icon
        )
    }
}