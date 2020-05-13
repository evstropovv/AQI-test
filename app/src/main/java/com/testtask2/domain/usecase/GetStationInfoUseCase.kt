package com.testtask2.domain.usecase

import com.testtask2.domain.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.StringBuilder
import javax.inject.Inject

class GetStationInfoUseCase @Inject constructor(
    private val repository: Repository
) {
    fun execute(id: Long, coroutineScope: CoroutineScope): Flow<String?> {
        return repository.getStationInfo(id = id, coroutineScope = coroutineScope).map {
            StringBuilder()
                .append("id: ").append(it?.id).append("\n")
                .append("coordinates: ").append(it?.lat).append(" ").append(it?.lon).append("\n")
                .append("pm10: ").append(it?.pm10).append("\n")
                .append("pm25: ").append(it?.pm25).append("\n")
                .append("refresh date: ").append(it?.updateTime.toString()).toString()
        }
    }
}