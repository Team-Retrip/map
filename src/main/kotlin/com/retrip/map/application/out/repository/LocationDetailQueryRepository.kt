package com.retrip.map.application.out.repository

import com.retrip.map.application.`in`.response.LocationDetailResponse
import com.retrip.map.domain.entity.LocationDetail
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime
import java.util.*

interface LocationDetailQueryRepository {
    fun findLocationDetailsByPage(locationId: UUID, id: UUID?, page: Pageable): Page<LocationDetailResponse>
    fun findLocationDetails(locationDetailIds: List<UUID>): List<LocationDetailResponse>
    fun findLocationDetailsByEditedAt(editedAt: LocalDateTime): List<LocationDetail>
}
