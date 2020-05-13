package com.testtask2.domain.model

import com.testtask2.domain.model.delegate.DisplayableItem
import java.util.*

data class StationInfo(val id : Long,
                       val lat: Double,
                       val lon: Double,
                       val updateTime: Date,
                       val pm25: Double,
                       val pm10: Double): DisplayableItem