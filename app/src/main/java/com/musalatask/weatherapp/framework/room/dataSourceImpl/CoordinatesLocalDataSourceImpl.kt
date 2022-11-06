package com.musalatask.weatherapp.framework.room.dataSourceImpl

import android.util.Log
import androidx.room.withTransaction
import com.musalatask.weatherapp.data.local.CoordinatesLocalDataSource
import com.musalatask.weatherapp.data.model.Coordinates
import com.musalatask.weatherapp.data.model.toCoordinatesEntity
import com.musalatask.weatherapp.framework.room.db.AppDataBase
import com.musalatask.weatherapp.framework.room.entity.toCoordinates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CoordinatesLocalDataSourceImpl @Inject constructor(
    private val db: AppDataBase
) : CoordinatesLocalDataSource {

    override suspend fun getCoordinatesOfACity(cityName: String): Coordinates? {
        Log.d("qqqqqqqq", "get $cityName coordinates from db")
        return db.coordinatesDao().findByName(cityName)?.toCoordinates()
    }

    override suspend fun insertCoordinates(vararg coordinates: Coordinates) {
        db.coordinatesDao().insertAll(coordinates.map { it.toCoordinatesEntity() })
    }

    override suspend fun deleteCoordinates(cityName: String) {
        withTransaction {
            db.coordinatesDao().delete(cityName)
        }
    }

    override suspend fun <R> withTransaction(block: suspend () -> R): R =
        db.withTransaction(block)
}