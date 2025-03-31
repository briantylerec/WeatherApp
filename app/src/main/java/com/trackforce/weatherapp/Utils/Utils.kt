package com.trackforce.weatherapp.Utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun timestampToTime(timestamp: Long): String {
    val date = Date(timestamp * 1000)
    val format = SimpleDateFormat("EE, MMM d;hh:mm a", Locale.ENGLISH)
    return format.format(date)
}

fun celsiusToFahrenheit(celsius: Double): Double {
    return (celsius * 9 / 5) + 32
}

fun getUvInfo(uvIndex: Double): String {
    return when {
        uvIndex in 0.0..2.9 -> "$uvIndex LOW-Minimum protection required"
        uvIndex in 3.0..5.9 -> "$uvIndex MODERATE-Wear sunglasses, sunscreen (SPF 30+), seek shade during peak hours"
        uvIndex in 6.0..7.9 -> "$uvIndex HIGH-Hat, sunglasses, sunscreen (SPF 30+), limit sun exposure"
        uvIndex in 8.0..10.9 -> "$uvIndex VERY HIGH-Avoid long exposure, wear protective clothing, sunscreen (SPF 50+)"
        uvIndex >= 11.0 -> "$uvIndex EXTREME-Very high risk, avoid peak sun hours, maximum protection"
        else -> "Invalid UV Index"
    }
}

