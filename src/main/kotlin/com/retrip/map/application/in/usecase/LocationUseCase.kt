package com.retrip.map.application.`in`.usecase

import com.retrip.map.application.`in`.request.LocationCreateRequest
import com.retrip.map.application.`in`.request.LocationUpdateRequest
import com.retrip.map.application.`in`.response.LocationCreateResponse
import com.retrip.map.application.`in`.response.LocationResponse
import com.retrip.map.application.`in`.response.LocationUpdateResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.UUID

interface LocationUseCase {
    fun createLocation(request: LocationCreateRequest): LocationCreateResponse
    fun updateLocation(id: UUID, request: LocationUpdateRequest): LocationUpdateResponse
    fun deleteLocation(locationId: UUID)
    fun getLocation(id: UUID?, page: Pageable): Page<LocationResponse>
}
