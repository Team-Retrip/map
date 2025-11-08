package com.retrip.map.infra.adapter.`in`.batch

import co.elastic.clients.elasticsearch.ElasticsearchClient
import com.retrip.map.application.out.repository.LocationElasticRepository
import com.retrip.map.application.out.repository.LocationQueryRepository
import com.retrip.map.domain.entity.Location
import org.springframework.batch.item.ItemReader
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime

@Component
class MapReader(
    private val locationQueryRepository: LocationQueryRepository,
    private val locationElasticRepository: LocationElasticRepository
): ItemReader<List<Location>> {
    override fun read(): List<Location>? {
        val lastUpdateDocument = locationElasticRepository.findFirstByOrderByEditedAtDesc()
        return locationQueryRepository.findLocationsByEditedAt(lastUpdateDocument?.editedAt ?: LocalDateTime.now())
    }
}
