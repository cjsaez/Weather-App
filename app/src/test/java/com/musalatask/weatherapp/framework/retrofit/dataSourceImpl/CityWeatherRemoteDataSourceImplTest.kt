package com.musalatask.weatherapp.framework.retrofit.dataSourceImpl

import com.google.gson.Gson
import com.musalatask.weatherapp.framework.retrofit.CityWeatherApi
import com.musalatask.weatherapp.framework.retrofit.dto.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class CityWeatherRemoteDataSourceImplTest {

    private lateinit var remoteDataSource: CityWeatherRemoteDataSourceImpl
    private lateinit var testApis: CityWeatherApi
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {

        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        testApis = retrofit.create(CityWeatherApi::class.java)

        remoteDataSource = CityWeatherRemoteDataSourceImpl(testApis, Dispatchers.IO)
    }

    @After
    fun shutdown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `data source must be provide a city weather object by its coordinates`() = runTest {
        val weather = CityWeatherDto(
                base = null,
                clouds = null,
                cod = null,
                coord = Coord(12.7, 344.9),
                dt = 2,
                id = 32,
                main = Main(23.32, 23, 4, 453.5, 45.5, 65.6),
                name = "Oslo",
                sys = Sys("Noruega", 32, 345, 565, 76),
                timezone = 90,
                visibility = null,
                weather = listOf(),
                wind = Wind(3434, 4535.5, 345.53),
                rain = null,
                snow = null
            )
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(weather))
        mockWebServer.enqueue(expectedResponse)

        val result = remoteDataSource.getCityWeather(322.3, 58.4)
        assertNotNull("The result is not null", result)
        assertTrue("The result is the correct", result == weather.toCityWeather())
    }
}