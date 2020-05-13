package com.testtask2.data

import com.testtask2.data.remote.models.StationInfoDto
import com.testtask2.data.remote.models.StationListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRetrofit {

    @GET("map/bounds/")
    suspend fun getStationListByCoordinates(@Query("latlng") userId: String): StationListDto

    @GET("feed/@{id}/")
    suspend fun getStationDetails(@Path("id") id: Long): StationInfoDto

}