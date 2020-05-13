package com.testtask2.data.cache.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class StationInfoDB(
    @PrimaryKey
    var id: Long = 0,
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val updateTime: String,
    val pm25: Double = 0.0,
    val pm10: Double = 0.0
)