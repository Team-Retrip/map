package com.retrip.map.application.out.repository

import com.retrip.map.application.`in`.response.LocationResponse
import com.retrip.map.domain.entity.Location
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDate
import java.util.UUID

interface LocationQueryRepository {
    fun findLocations(id: UUID?, page: Pageable): Page<LocationResponse>
    fun findLocationsByEditedAt( editedAt: LocalDate): List<Location>
}
