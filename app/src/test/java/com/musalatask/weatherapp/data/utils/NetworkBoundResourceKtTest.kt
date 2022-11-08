package com.musalatask.weatherapp.data.utils

import com.musalatask.weatherapp.common.Resource
import com.musalatask.weatherapp.domain.model.CityWeather
import com.musalatask.weatherapp.domain.model.Coordinates
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class NetworkBoundResourceKtTest {

    lateinit var weatherQuery: suspend () -> CityWeather?
    lateinit var weatherFetch: suspend () -> CityWeather?
    lateinit var weatherSaveFetchResult: suspend (CityWeather) -> Unit

    lateinit var coordinatesQuery: suspend () -> Coordinates?
    lateinit var coordinatesFetch: suspend () -> Coordinates?
    lateinit var coordinatesSaveFetchResult: suspend (Coordinates) -> Unit

    lateinit var coordinatesQuery2: suspend () -> Coordinates?
    lateinit var coordinatesFetch2: suspend () -> Coordinates?
    lateinit var coordinatesSaveFetchResult2: suspend (Coordinates) -> Unit

    @Before
    fun setUp() {
        var weather1 = CityWeather(
            description = "broken clouds",
            iconId = "04n",
            temp = 284.51,
            feelsLike = 283.89,
            tempMin = 283.97,
            tempMax = 284.96,
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
            cityName = "Sofia",
            lastUpdated = null,
            latitude = 42.6977028,
            longitude = 23.3217359
        )
        val weather2 = CityWeather(
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
        weatherQuery = {weather1}
        weatherFetch = {weather2}
        weatherSaveFetchResult = {weather1 = weather2}

        var coordinates1 = Coordinates(
            latitude = 59.9133301,
            longitude = 10.7389701,
            cityName = "Oslo",
            names = mutableListOf()
        )
        val coordinates2 =  Coordinates(
            latitude = 42.6977028,
            longitude = 23.3217359,
            cityName = "Sofia",
            names = mutableListOf()
        )
        var coordinates3: Coordinates? = null
        val coordinates4: Coordinates? =  null

        coordinatesQuery = {coordinates1}
        coordinatesFetch = {coordinates2}
        coordinatesSaveFetchResult = {coordinates1 = coordinates2}

        coordinatesQuery2 = {coordinates3}
        coordinatesFetch2 = {coordinates4}
        coordinatesSaveFetchResult2 = {coordinates3 = coordinates4}
    }

    @Test
    fun `getNetworkBoundWeatherResource function must get the expected flow of weathers`() = runTest {
        val getCityWeather = getNetworkBoundWeatherResource(weatherQuery, weatherFetch, weatherSaveFetchResult).take(3)

        val results = getCityWeather.toList()
        assertTrue("It was emitted 3 resources", results.size == 3)
        assertTrue("First resource must be a Loading", results[0] is Resource.Loading)
        assertTrue("Second and third resources must be Success",
            results[1] is Resource.Success && results[2] is Resource.Success)
    }

    @Test
    fun `getNetworkBoundCoordinatesResource function must get the expected flow of weathers`() = runTest {
        val getCoordinates = getNetworkBoundCoordinatesResource(coordinatesQuery, coordinatesFetch, coordinatesSaveFetchResult).take(3)

        val results = getCoordinates.toList()
        assertTrue("It was emitted 2 resources", results.size == 2)
        assertTrue("First resource must be a Loading", results[0] is Resource.Loading)
        assertTrue("Second resources must be Success", results[1] is Resource.Success)

        val getCoordinates2 = getNetworkBoundCoordinatesResource(coordinatesQuery2, coordinatesFetch2, coordinatesSaveFetchResult2).take(2)

        val results2 = getCoordinates2.toList()
        assertTrue("It was emitted 2 resources", results2.size == 2)
        assertTrue("First resource must be a Loading", results2[0] is Resource.Loading)
        assertTrue("Second resources must be Error", results2[1] is Resource.Error)
    }
}