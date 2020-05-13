package com.testtask2.data.remote.models

data class StationListDto(
    val status: String?,
    val `data`: List<DataDTO>?
) {
    data class DataDTO(
        val lat: Double?,
        val lon: Double?,
        val uid: Long?,
        val aqi: String?,
        val station: StationDTO?
    ) {
        data class StationDTO(
            val name: String?,
            val time: String?
        )
    }
}