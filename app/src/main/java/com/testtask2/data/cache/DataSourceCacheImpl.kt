package com.testtask2.data.cache

import com.testtask2.data.cache.db.AppDatabase
import com.testtask2.data.cache.db.StationsDB
import com.testtask2.data.datasource.DataSourceCache
import com.testtask2.data.mapper.DataMapper
import com.testtask2.domain.model.Station
import com.testtask2.domain.model.StationInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataSourceCacheImpl @Inject constructor(
    private val db: AppDatabase,
    private val globalMapper: DataMapper
) : DataSourceCache {

    override fun getStationInfo(id: Long): Flow<StationInfo?> {
        return db.stationInfo()
            .getStation(id)
            .map {
                globalMapper.toDomain(it)
            }
    }


    override suspend fun saveStation(station: StationInfo) {
        db.stationInfo().insert(globalMapper.toBd(station = station))
    }

    override suspend fun saveStations(stationList: List<Station>) {
        val stations: MutableList<StationsDB> = ArrayList()
        stationList.forEach {
            stations.add(globalMapper.toBd(it))
        }
        db.stations().insert(stations)
    }

    override fun getStationList(minAqi: Int): Flow<List<Station>> {
        return db.stations().getStations(minAqi)
            .map<List<StationsDB>, List<Station>> { st ->
                val stations: MutableList<Station> = ArrayList()
                st.forEach {
                    stations.add(globalMapper.toDomain(it))
                }
                stations
            }
    }

    override suspend fun clearStations() = db.stations().clearTable()
}