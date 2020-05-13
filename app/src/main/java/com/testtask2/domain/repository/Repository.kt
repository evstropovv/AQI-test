package com.testtask2.domain.repository

import com.testtask2.domain.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getStations(currentLatLng: LatLng, radiusInKm: Int, aqi: Int, coroutineScope: CoroutineScope): Flow<List<Station>?>
    fun getStationInfo(id: Long, coroutineScope: CoroutineScope): Flow<StationInfo?>
}