package com.testtask2.data.cache.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class StationsDB (
    @PrimaryKey
    var id: Long = 0,
    val name: String="",
    val aqi: Int = 0
)