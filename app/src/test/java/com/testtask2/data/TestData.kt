package com.testtask2.data

import com.testtask2.data.cache.db.StationInfoDB
import com.testtask2.data.cache.db.StationsDB
import com.testtask2.data.remote.models.StationInfoDto
import com.testtask2.data.remote.models.StationListDto
import com.testtask2.domain.model.StationInfo
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object TestData {
    fun getStationDbList(): List<StationsDB> {
        val stations: MutableList<StationsDB> = ArrayList()
        stations.add(getStationsDB())
        return stations
    }


    fun getStationsDB() = StationsDB(1, "", 1)

    fun getStationInfoDB() = StationInfoDB(1, 0.0, 0.0, SimpleDateFormat("d-MMM-yyyy,HH:mm:ss aaa", Locale.getDefault()).format(Date(0)), 0.0, 0.0)

    fun getStationInfo() = StationInfo(1, 0.0, 0.0, Date(0), 0.0, 0.0)

    fun getStationInfoDto() = StationInfoDto(
        "", StationInfoDto.DataDTO(
            0,
            0,
            arrayListOf(StationInfoDto.DataDTO.AttributionDTO("", "", "")),
            StationInfoDto.DataDTO.CityDTO(
                arrayListOf(0.0, 0.0), "", ""
            ),
            "",
            StationInfoDto.DataDTO.IaqiDTO(),
            StationInfoDto.DataDTO.TimeDTO("2020-05-13 02:00:00", "-07:00", 0)
            ,
            StationInfoDto.DataDTO.DebugDTO("")
        )
    )


    fun StationListDto() = StationListDto(
        "", arrayListOf(
            StationListDto.DataDTO(0.0, 0.0, 0L, "", StationListDto.DataDTO.StationDTO("", ""))
        )
    )

}