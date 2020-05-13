package com.testtask2.data.remote.models

data class StationInfoDto(
    val status: String?,
    val `data`: DataDTO?
) {
    data class DataDTO(
        val aqi: Int,
        val idx: Int,
        val attributions: List<AttributionDTO?>? = listOf(),
        val city: CityDTO?,
        val dominentpol: String?,
        val iaqi: IaqiDTO? = IaqiDTO(),
        val time: TimeDTO?,
        val debug: DebugDTO?
    ) {
        data class CityDTO(
            val geo: List<Double?>?,
            val name: String?,
            val url: String?
        )

        data class IaqiDTO(
            val dew: DewDTO? = DewDTO(),
            val h: HDTO?= HDTO(),
            val p: PDTO?= PDTO(),
            val pm10: Pm10DTO?= Pm10DTO(),
            val pm25: Pm25DTO?= Pm25DTO(),
            val t: TDTO?= TDTO(),
            val w: WDTO?= WDTO(),
            val wg: WgDTO?=WgDTO()
        ) {
            data class Pm25DTO(
                val v: Double? = 0.0
            )

            data class DewDTO(
                val v: Double? = 0.0
            )

            data class PDTO(
                val v: Double? = 0.0
            )

            data class HDTO(
                val v: Double? = 0.0
            )

            data class TDTO(
                val v: Double? = 0.0
            )

            data class WDTO(
                val v: Double? = 0.0
            )

            data class WgDTO(
                val v: Double? = 0.0
            )

            data class Pm10DTO(
                val v: Double? = 0.0
            )
        }

        data class AttributionDTO(
            val url: String?,
            val name: String?,
            val logo: String?
        )

        data class TimeDTO(
            val s: String?,
            val tz: String?,
            val v: Int?
        )

        data class DebugDTO(
            val sync: String?
        )
    }
}