package com.musalatask.weatherapp.domain.model

import com.musalatask.weatherapp.framework.room.entity.toCoordinates
import junit.framework.TestCase
import org.junit.Test

internal class CoordinatesTest{

    @Test
    fun `a coordinates can be mapped to coordinates entity`(){
        val coordinates = Coordinates(
            latitude = 42.6977028,
            longitude = 23.3217359,
            cityName = "Sofia",
            names = mutableListOf()
        )

        val coordinates2 = coordinates.toCoordinatesEntity().toCoordinates()

        TestCase.assertTrue(
            "The coordinates and the generated one must be equals",
            coordinates == coordinates2
        )
    }
}