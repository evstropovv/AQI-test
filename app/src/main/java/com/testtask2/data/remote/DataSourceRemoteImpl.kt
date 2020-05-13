package com.testtask2.data.remote

import com.testtask2.data.ApiRetrofit
import com.testtask2.data.datasource.DataSourceRemote
import com.testtask2.data.mapper.DataMapper
import com.testtask2.domain.model.*
import javax.inject.Inject

class DataSourceRemoteImpl @Inject constructor(
    private val apiRetrofit: ApiRetrofit,
    private val mapper: DataMapper
) : DataSourceRemote {


    override suspend fun getStations(latlng: String): List<Station>? {
        return try {
            return apiRetrofit.getStationListByCoordinates(latlng).data?.map {
                mapper.toDomain(it)
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getStationInfo(id: Long): StationInfo? {
        return try {
            val info = apiRetrofit.getStationDetails(id).data
            info?.let {
                return mapper.toDomain(it)
            }
        } catch (e: Exception){
            return null
        }
    }

}