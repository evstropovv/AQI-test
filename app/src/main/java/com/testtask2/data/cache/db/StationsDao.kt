package com.testtask2.data.cache.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StationsDao {

    @Query("SELECT * FROM stationsdb WHERE aqi > :minAqi")
    fun getStations(minAqi: Int): Flow<List<StationsDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(employee: List<StationsDB>)

    @Query("DELETE FROM stationsdb")
    fun clearTable()

}