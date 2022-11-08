package com.musalatask.weatherapp.framework.retrofit.dataSourceImpl

import com.google.gson.Gson
import com.musalatask.weatherapp.common.Constants
import com.musalatask.weatherapp.domain.model.Coordinates
import com.musalatask.weatherapp.framework.retrofit.GeocodingApi
import com.musalatask.weatherapp.framework.retrofit.SecurityInterceptor
import com.musalatask.weatherapp.framework.retrofit.dto.CoordinatesDto
import com.musalatask.weatherapp.framework.retrofit.dto.toCoordinates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class CoordinatesRemoteDataSourceImplTest {

    private lateinit var remoteDataSource: CoordinatesRemoteDataSourceImpl
    private lateinit var testApis: GeocodingApi
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {

        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        testApis = retrofit.create(GeocodingApi::class.java)

        remoteDataSource = CoordinatesRemoteDataSourceImpl(testApis, Dispatchers.IO)
    }

    @After
    fun shutdown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `data source must be provide a coordinates object by its city name`() = runTest {
        val coordinates = listOf(
            CoordinatesDto(
                country = "Japan",
                lat = 322.3,
                lon = 58.4,
                name = "Tokyo",
                state = "Unknown"
            ),
            CoordinatesDto(
                country = "Bulgaria",
                lat = 32.3,
                lon = -58.4,
                name = "Sofia",
                state = "Unknown"
            )
        )
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(coordinates))
        mockWebServer.enqueue(expectedResponse)

        val result = remoteDataSource.getCoordinatesOfACity("Tokyo")
        assertNotNull("The result is not null", result)
        assertTrue("The result is the correct", result == coordinates[0].toCoordinates())
    }

    @Test
    fun `data source must be provide a coordinates object by its coordinates`() = runTest {
        val coordinates = listOf(
            CoordinatesDto(
                country = "Japan",
                lat = 322.3,
                lon = 58.4,
                name = "Tokyo",
                state = "Unknown"
            ),
            CoordinatesDto(
                country = "Bulgaria",
                lat = 32.3,
                lon = -58.4,
                name = "Sofia",
                state = "Unknown"
            )
        )
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(coordinates))
        mockWebServer.enqueue(expectedResponse)

        val result = remoteDataSource.getCoordinates(322.3, 58.4)
        assertNotNull("The result is not null", result)
        assertTrue("The result is the correct", result == coordinates[0].toCoordinates())
    }
}