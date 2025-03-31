package com.trackforce.weatherapp.domain.usecase

import com.trackforce.weatherapp.domain.model.Weather
import com.trackforce.weatherapp.domain.repository.WeatherRepository
import com.trackforce.weatherapp.preferences.SecurePreferences
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(private val repository: WeatherRepository, private val securePreferences: SecurePreferences,) {
    suspend operator fun invoke(lat: Double, lon: Double): Weather {
        return repository.getWeather(lat, lon)
    }
}
