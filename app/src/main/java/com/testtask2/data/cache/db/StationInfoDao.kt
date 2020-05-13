package com.testtask2.data.cache.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StationInfoDao {

    @Query("SELECT * FROM stationinfodb WHERE id = :id")
    fun getStation(id: Long): Flow<StationInfoDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(station: StationInfoDB)

    @Delete
    fun delete(station: StationInfoDB)

}