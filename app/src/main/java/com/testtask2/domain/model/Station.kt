package com.testtask2.domain.model

import com.testtask2.domain.model.delegate.DisplayableItem

data class Station(val id : Long,
                   val name: String,
                   val aqi: Int): DisplayableItem