package com.testtask2.data.repository

import com.testtask2.data.datasource.DataSourceCache
import com.testtask2.data.datasource.DataSourceRemote
import com.testtask2.domain.helper.Helper
import com.testtask2.domain.model.*
import com.testtask2.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.testtask2.domain.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RepositoryImpl @Inject constructor(
    private val dataSourceRemote: DataSourceRemote,
    private val dataSourceCache: DataSourceCache
) : Repository {

    override fun getStations(
        currentLatLng: LatLng,
        radiusInKm: Int,
        aqi: Int,
        coroutineScope: CoroutineScope
    ): Flow<List<Station>?> {

        coroutineScope.launch(Dispatchers.IO) {

            val stations = dataSourceRemote.getStations(
                Helper.toBounds(currentLatLng, (radiusInKm * 1000).toDouble())
            )
            if (stations != null) {
                dataSourceCache.clearStations()
                dataSourceCache.saveStations(stations)
            }
        }
        return dataSourceCache.getStationList(aqi)
    }

    override fun getStationInfo(id: Long, coroutineScope: CoroutineScope): Flow<StationInfo?> {
        coroutineScope.launch(Dispatchers.IO) {
            dataSourceRemote.getStationInfo(id)?.let {
                dataSourceCache.saveStation(it)
            }
        }
        return dataSourceCache.getStationInfo(id)
    }

}