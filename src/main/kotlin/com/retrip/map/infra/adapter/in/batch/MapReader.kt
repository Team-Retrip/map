package com.retrip.map.infra.adapter.`in`.batch

import com.retrip.map.application.out.repository.LocationQueryRepository
import com.retrip.map.domain.entity.Location
import org.springframework.batch.item.ItemReader
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class MapReader(
    val locationQueryRepository: LocationQueryRepository

): ItemReader<List<Location>> {
    override fun read(): List<Location>? {
        println("TEST")
        return locationQueryRepository.findLocationsByEditedAt(LocalDate.now())
    }
}
