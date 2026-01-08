package com.retrip.map.infra.adapter.`in`.batch

import com.retrip.map.application.out.repository.LocationDetailElasticRepository
import com.retrip.map.application.out.repository.LocationDetailQueryRepository
import com.retrip.map.domain.entity.LocationDetail
import org.springframework.batch.item.ItemReader
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Component
class MapReader(
    private val locationDetailQueryRepository: LocationDetailQueryRepository,
    private val locationDetailElasticRepository: LocationDetailElasticRepository
) : ItemReader<List<LocationDetail>> {
    override fun read(): List<LocationDetail>? {
        val lastUpdateDocument = locationDetailElasticRepository.findFirstByOrderByEditedAtDesc()
        val editedAt =
            lastUpdateDocument?.editedAt?.let { LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneOffset.UTC) }
                ?: LocalDateTime.now()

        return locationDetailQueryRepository.findLocationDetailsByEditedAt(editedAt)
    }
}
