package com.retrip.map.application.out.repository

import com.retrip.map.domain.entity.Location
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.util.UUID

interface LocationRepository: JpaRepository<Location, UUID> {
    fun findFirstByNameValueAndCountryValue(name: String, country: String): Location?
    fun findByEditedAtAfter(editedAt: LocalDateTime): List<Location>

    @Query("SELECT l.id FROM Location l WHERE l.id IS NOT NULL")
    fun findAllIds(): List<UUID>
}
