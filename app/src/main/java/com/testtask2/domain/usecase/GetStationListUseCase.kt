package com.testtask2.domain.usecase

import android.location.Location
import com.testtask2.domain.model.LatLng
import com.testtask2.domain.model.Station
import com.testtask2.domain.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStationListUseCase @Inject constructor(
    private val repository: Repository
) {
    fun execute(location: Location, radius: Int, minAqi: Int, coroutineScope: CoroutineScope): Flow<List<Station>?> {
        return repository.getStations(LatLng(location.latitude, location.longitude), radius, minAqi, coroutineScope)
    }
}