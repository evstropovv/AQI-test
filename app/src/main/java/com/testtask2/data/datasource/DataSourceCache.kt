package com.testtask2.data.datasource

import com.testtask2.domain.model.*
import kotlinx.coroutines.flow.Flow

interface DataSourceCache {

    fun getStationList(minAqi: Int): Flow<List<Station>>
    suspend fun saveStations(stationList: List<Station>)
    suspend fun clearStations()

    fun getStationInfo(id: Long) : Flow<StationInfo?>
    suspend fun saveStation(station: StationInfo)
}