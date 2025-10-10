package com.retrip.map.application.out.repository

import com.retrip.map.application.`in`.response.LocationResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.UUID

interface LocationQueryRepository {
    fun findLocations(id: UUID?, page: Pageable): Page<LocationResponse>
}
