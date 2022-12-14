package com.musalatask.weatherapp.domain.model

import com.musalatask.weatherapp.framework.room.entity.CityWeatherEntity

data class CityWeather(
    val description: String?,
    val iconId: String?,
    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Int,
    val humidity: Int,
    val visibility: Int?,
    val windSpeed: Double,
    val windDirection: Int,
    val cloudiness: Int?,
    val rainVolumeLastHour: Double?,
    val snowVolumeLastHour: Double?,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int,
    var cityName: String,
    var lastUpdated: Long?,
    val latitude: Double,
    val longitude: Double
)

/**
 * Map a city weather model object into a room city weather entity object.
 */
fun CityWeather.toCityWeatherEntity(): CityWeatherEntity =
    CityWeatherEntity(
        description = description,
        iconId = iconId,
        temp = temp,
        feelsLike = feelsLike,
        tempMin = tempMin,
        tempMax = tempMax,
        pressure = pressure,
        humidity = humidity,
        visibility = visibility,
        windSpeed = windSpeed,
        windDirection = windDirection,
        cloudiness = cloudiness,
        rainVolumeLastHour = rainVolumeLastHour,
        snowVolumeLastHour = snowVolumeLastHour,
        sunrise = sunrise,
        sunset = sunset,
        timezone = timezone,
        cityName = cityName,
        lastUpdated = lastUpdated,
        latitude = latitude,
        longitude = longitude
    )