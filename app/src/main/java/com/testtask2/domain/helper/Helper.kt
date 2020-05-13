package com.testtask2.domain.helper

import com.testtask2.domain.model.LatLng

object Helper {

    fun toBounds(center: LatLng, radiusInMeters: Double): String {
        val distanceFromCenterToCorner = radiusInMeters * Math.sqrt(2.0)
        val southwestCorner = computeOffset(center, distanceFromCenterToCorner, 225.0)
        val northeastCorner = computeOffset(center, distanceFromCenterToCorner, 45.0)
        val sb = StringBuilder()
        sb.append(southwestCorner.latitude).append(",")
        sb.append(southwestCorner.longitude).append(",")
        sb.append(northeastCorner.latitude).append(",")
        sb.append(northeastCorner.longitude)
        return sb.toString()
    }


    private fun computeOffset(from: LatLng, distance: Double, heading: Double): LatLng {
        var distance = distance
        var heading = heading
        distance /= 6371009.0
        heading = Math.toRadians(heading)
        val fromLat = Math.toRadians(from.latitude)
        val fromLng = Math.toRadians(from.longitude)
        val cosDistance = Math.cos(distance)
        val sinDistance = Math.sin(distance)
        val sinFromLat = Math.sin(fromLat)
        val cosFromLat = Math.cos(fromLat)
        val sinLat = cosDistance * sinFromLat + sinDistance * cosFromLat * Math.cos(heading)
        val dLng = Math.atan2(
            sinDistance * cosFromLat * Math.sin(heading),
            cosDistance - sinFromLat * sinLat
        )
        return LatLng(Math.toDegrees(Math.asin(sinLat)), Math.toDegrees(fromLng + dLng))
    }

}