package com.openweather.app.utils.extension

import com.google.common.truth.Truth
import org.amshove.kluent.shouldEqual
import org.junit.Test


class StringExtensionsKtTest {
    @Test
    fun `isEmailValid should return true for valid email`() {
        var result = "saurabh@gmail.com".isEmailValid()

        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `isEmailValid should return false for invalid email`() {
        var result = "saurabh@.com".isEmailValid()

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `convertTimeMillisToFormattedString should return valid formatted string`() {
        var time = 1707022723555
        var result = time.convertTimeMillisToFormattedString("HH:mm:ss")

        println(result)

        result.shouldEqual("16:15:55")
    }

    @Test
    fun `convertTemperatureToCelsius should return valid celsius`() {
        var kelvin = 276.0
        var result = kelvin.convertTemperatureToCelsius()

        println(result)

        result.shouldEqual("2.85°C")
    }

    @Test
    fun `test getCountryNameFromCode with valid country code`() {
        // Arrange
        val countryCode = "US"

        // Act
        val countryName = countryCode.getCountryNameFromCode()

        // Assert
        countryName.shouldEqual("United States")
    }

    @Test
    fun `test getCountryNameFromCode with another valid country code`() {
        // Arrange
        val countryCode = "IN"

        // Act
        val countryName = countryCode.getCountryNameFromCode()

        // Assert
        countryName.shouldEqual("India")
    }

    @Test
    fun `test getCountryNameFromCode with empty country code`() {
        // Arrange
        val countryCode = ""

        // Act
        val countryName = countryCode.getCountryNameFromCode()

        // Assert
        countryName.shouldEqual("")  // Locale returns an empty string for empty country codes
    }

    @Test
    fun `test getCountryNameFromCode with lowercase country code`() {
        // Arrange
        val countryCode = "us"

        // Act
        val countryName = countryCode.getCountryNameFromCode()

        // Assert
        countryName.shouldEqual("United States")  // Locale should handle lowercase codes correctly
    }
}
