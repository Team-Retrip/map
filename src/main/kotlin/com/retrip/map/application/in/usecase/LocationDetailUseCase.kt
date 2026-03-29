package com.retrip.map.application.`in`.usecase

import com.retrip.map.application.`in`.request.LocationDetailCreateRequest
import com.retrip.map.application.`in`.request.LocationDetailUpdateRequest
import com.retrip.map.application.`in`.response.LocationDetailCreateResponse
import com.retrip.map.application.`in`.response.LocationDetailResponse
import com.retrip.map.application.`in`.response.LocationDetailUpdateResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface LocationDetailUseCase {
    fun createLocationDetail(locationId: UUID, request: LocationDetailCreateRequest): LocationDetailCreateResponse
    fun updateLocationDetail(locationId: UUID, id: UUID, request: LocationDetailUpdateRequest): LocationDetailUpdateResponse
    fun deleteLocationDetail(locationDetailId: UUID)
    fun getLocationDetail(locationId: UUID, id: UUID?, page: Pageable): Page<LocationDetailResponse>
    fun getLocationDetails(locationDetailIds: List<UUID>): List<LocationDetailResponse>
}
