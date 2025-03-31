package com.trackforce.weatherapp.data.remote

import com.trackforce.weatherapp.data.remote.response.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("onecall")
    suspend fun getWeatherByCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric",
        @Query("exclude") exclude: String = "hourly,daily,minutely", // To exclude unnecessary data
        @Query("appid") appid: String = "d91b4744c7c34b670ca27d04b5208e22" // API Key
    ): Response<WeatherResponse>
}

