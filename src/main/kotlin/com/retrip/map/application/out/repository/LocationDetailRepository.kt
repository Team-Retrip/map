package com.retrip.map.application.out.repository

import com.retrip.map.domain.entity.LocationDetail
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface LocationDetailRepository: JpaRepository<LocationDetail, UUID> {
    fun findByNameValueAndLocationId(name: String, locationId: UUID): LocationDetail?
}
