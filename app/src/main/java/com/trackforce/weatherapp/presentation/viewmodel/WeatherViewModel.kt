package com.trackforce.weatherapp.presentation.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.trackforce.weatherapp.domain.model.Weather
import com.trackforce.weatherapp.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase) : ViewModel() {

    private val _weatherData = MutableStateFlow<Weather?>(null)
    val weatherData: StateFlow<Weather?> = _weatherData.asStateFlow() as StateFlow<Weather>

    @SuppressLint("MissingPermission")
    fun fetchWeatherWithLocation(fusedLocationClient: FusedLocationProviderClient) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                location?.let {
                    fetchWeather(it.latitude, it.longitude)
                    Log.i("WeatherViewModel", "Lat: ${it.latitude}, Lo: ${it.longitude}")
                }
                    ?: Log.e("WeatherViewModel", "Couldnt get location")
            }
            .addOnFailureListener { exception ->
                Log.e("WeatherViewModel", "Error getting location", exception)
            }
    }

    private fun fetchWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                _weatherData.value = getWeatherUseCase(latitude, longitude)
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "Error getting weather", e)
            }
        }
    }
}
