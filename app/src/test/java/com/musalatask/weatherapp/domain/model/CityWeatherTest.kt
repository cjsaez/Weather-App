package com.musalatask.weatherapp.domain.model

import com.musalatask.weatherapp.framework.room.entity.toCityWeather
import junit.framework.TestCase.assertTrue
import org.junit.Test


internal class CityWeatherTest{

    @Test
    fun `a city weather can be mapped to city weather entity`(){
        val weather = CityWeather(
            description = "broken clouds",
            iconId = "04n",
            temp = 284.51,
            feelsLike = 283.89,
            tempMin = 283.97,
            tempMax =  284.96,
            pressure = 1019,
            humidity = 84,
            visibility = 10000,
            windSpeed = 2.06,
            windDirection = 160,
            cloudiness = 75,
            rainVolumeLastHour = null,
            snowVolumeLastHour = null,
            sunrise = 1667711196,
            sunset = 1667747644,
            timezone = 7200,
            cityName = "Ontario",
            lastUpdated = null,
            latitude = 34.065846,
            longitude = -117.6484304
        )

        val weather2 = weather.toCityWeatherEntity().toCityWeather()

        assertTrue("The city weather and the generated one must be equals", weather == weather2)
    }
}