package com.trackforce.weatherapp.utils

import com.trackforce.weatherapp.Utils.celsiusToFahrenheit
import com.trackforce.weatherapp.Utils.getUvInfo
import com.trackforce.weatherapp.Utils.timestampToTime
import junit.framework.TestCase.assertEquals
import org.junit.Test

class UtilsTest {

    @Test
    fun `test timestampToTime returns correct formatted time`() {
        val timestamp: Long = 1633035600
        val expected = "Thu, Sep 30;04:00 PM"

        val result = timestampToTime(timestamp)

        assertEquals(expected, result)
    }

    @Test
    fun `test celsiusToFahrenheit converts correctly`() {
        val celsius = 0.0
        val expectedFahrenheit = 32.0

        val result = celsiusToFahrenheit(celsius)

        assertEquals(expectedFahrenheit, result)
    }

    @Test
    fun `test celsiusToFahrenheit converts negative value correctly`() {
        val celsius = -10.0
        val expectedFahrenheit = 14.0

        val result = celsiusToFahrenheit(celsius)

        assertEquals(expectedFahrenheit, result)
    }

    @Test
    fun `test getUvInfo for low UV index`() {
        val uvIndex = 2.0
        val expected = "2.0 LOW-Minimum protection required"

        val result = getUvInfo(uvIndex)

        assertEquals(expected, result)
    }

    @Test
    fun `test getUvInfo for moderate UV index`() {
        val uvIndex = 4.5
        val expected = "4.5 MODERATE-Wear sunglasses, sunscreen (SPF 30+), seek shade during peak hours"

        val result = getUvInfo(uvIndex)

        assertEquals(expected, result)
    }

    @Test
    fun `test getUvInfo for extreme UV index`() {
        val uvIndex = 12.0
        val expected = "12.0 EXTREME-Very high risk, avoid peak sun hours, maximum protection"

        val result = getUvInfo(uvIndex)

        assertEquals(expected, result)
    }

    @Test
    fun `test getUvInfo for invalid UV index`() {
        val uvIndex = -1.0
        val expected = "Invalid UV Index"

        val result = getUvInfo(uvIndex)

        assertEquals(expected, result)
    }
}
