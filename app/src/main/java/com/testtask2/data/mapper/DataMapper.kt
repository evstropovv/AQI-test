package com.testtask2.data.mapper

import com.testtask2.data.cache.db.StationInfoDB
import com.testtask2.data.cache.db.StationsDB
import com.testtask2.data.remote.models.StationInfoDto
import com.testtask2.data.remote.models.StationListDto
import com.testtask2.domain.model.Station
import com.testtask2.domain.model.StationInfo
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DataMapper @Inject constructor() {


    fun toDomain(dto: StationInfoDto.DataDTO?): StationInfo {
        val formatter = SimpleDateFormat("yyyy-MM-d HH:mm:ss XXX", Locale.getDefault()) //2020-05-13 12:00:00 +03:00
        return StationInfo(
            id = dto?.idx?.toLong() ?: -1,
            lat = dto?.city?.geo?.get(0) ?: 0.0,
            lon = dto?.city?.geo?.get(1) ?: 0.0,
            updateTime = formatter.parse("${dto?.time?.s} ${dto?.time?.tz}"),
            pm25 = dto?.iaqi?.pm25?.v ?: 0.0,
            pm10 = dto?.iaqi?.pm10?.v ?: 0.0
        )
    }

    fun toDomain(station: StationListDto.DataDTO): Station {
        return Station(
            id = station.uid ?: 0,
            name = station.station?.name ?: "",
            aqi = try {
                station.aqi?.toInt() ?: 0
            } catch (e: NumberFormatException) {
                0
            }
        )
    }

    fun toDomain(station: StationsDB): Station {
        return Station(
            id = station.id,
            name = station.name,
            aqi = station.aqi
        )
    }

    fun toBd(station: Station): StationsDB {
        return StationsDB(
            id = station.id,
            name = station.name,
            aqi = station.aqi
        )
    }

    fun toDomain(station: StationInfoDB?): StationInfo? {
        if (station != null) {
            val formatter = SimpleDateFormat("d-MMM-yyyy,HH:mm:ss aaa", Locale.getDefault())
            return StationInfo(
                id = station.id,
                lat = station.lat,
                lon = station.lon,
                updateTime = formatter.parse(station.updateTime)?:Date(),
                pm25 = station.pm25,
                pm10 = station.pm10

            )
        } else
            return null

    }

    fun toBd(station: StationInfo): StationInfoDB {
        val formatter = SimpleDateFormat("d-MMM-yyyy,HH:mm:ss aaa", Locale.getDefault())
        return StationInfoDB(
            id = station.id,
            lat = station.lat,
            lon = station.lon,
            updateTime = formatter.format(station.updateTime),
            pm25 = station.pm25,
            pm10 = station.pm10
        )
    }
}