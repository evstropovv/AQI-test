package com.testtask2.data.datasource

import com.testtask2.domain.model.*

interface DataSourceRemote {
    suspend fun getStations(latlng: String): List<Station>?
    suspend fun getStationInfo(id: Long): StationInfo?
}