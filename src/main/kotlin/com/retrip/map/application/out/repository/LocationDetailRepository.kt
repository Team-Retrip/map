package com.retrip.map.application.out.repository

import com.retrip.map.domain.entity.LocationDetail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.util.UUID

interface LocationDetailRepository: JpaRepository<LocationDetail, UUID> {
    fun findByNameValueAndLocationId(name: String, locationId: UUID): LocationDetail?
    fun findByEditedAtAfter(editedAt: LocalDateTime): List<LocationDetail>

    @Query("SELECT ld.id FROM LocationDetail ld WHERE ld.id IS NOT NULL")
    fun findAllIds(): List<UUID>
}
