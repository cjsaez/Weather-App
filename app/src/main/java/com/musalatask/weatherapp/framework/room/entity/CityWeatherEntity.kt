package com.musalatask.weatherapp.framework.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.musalatask.weatherapp.domain.model.CityWeather

@Entity(tableName = "city_weathers")
data class CityWeatherEntity(
    @PrimaryKey val cityName: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "icon_id") val iconId: String?,
    @ColumnInfo(name = "temperature") val temp: Double,
    @ColumnInfo(name = "feels_like") val feelsLike: Double,
    @ColumnInfo(name = "temp_min") val tempMin: Double,
    @ColumnInfo(name = "temp_max") val tempMax: Double,
    @ColumnInfo(name = "pressure") val pressure: Int,
    @ColumnInfo(name = "humidity") val humidity: Int,
    @ColumnInfo(name = "visibility") val visibility: Int?,
    @ColumnInfo(name = "wind_speed") val windSpeed: Double,
    @ColumnInfo(name = "wind_direction") val windDirection: Int,
    @ColumnInfo(name = "cloudiness") val cloudiness: Int?,
    @ColumnInfo(name = "rain_volume_last_hour") val rainVolumeLastHour: Double?,
    @ColumnInfo(name = "snow_volume_last_hour") val snowVolumeLastHour: Double?,
    @ColumnInfo(name = "sunrise") val sunrise: Int,
    @ColumnInfo(name = "sunset") val sunset: Int,
    @ColumnInfo(name = "timezone") val timezone: Int,
    @ColumnInfo(name = "last_updated") val lastUpdated: Long?,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double
)

/**
 * Map a room city weather entity object into a city weather model object.
 */
fun CityWeatherEntity.toCityWeather(): CityWeather =
    CityWeather(
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