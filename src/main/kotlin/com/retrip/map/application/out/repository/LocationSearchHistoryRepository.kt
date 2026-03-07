package com.retrip.map.application.out.repository

import com.retrip.map.domain.entity.LocationSearchHistory
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface LocationSearchHistoryRepository: JpaRepository<LocationSearchHistory, UUID> {
}
