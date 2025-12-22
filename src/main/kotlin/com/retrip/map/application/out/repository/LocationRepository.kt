package com.retrip.map.application.out.repository

import com.retrip.map.domain.entity.Location
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface LocationRepository: JpaRepository<Location, UUID> {
    fun findFirstByNameValueAndCountryValue(name: String, country: String): Location?
}
