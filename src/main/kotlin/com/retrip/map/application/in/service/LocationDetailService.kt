package com.retrip.map.application.`in`.service

import co.elastic.clients.elasticsearch.core.search.ContextBuilders.location
import com.retrip.map.application.`in`.request.LocationDetailCreateRequest
import com.retrip.map.application.`in`.request.LocationDetailUpdateRequest
import com.retrip.map.application.`in`.response.LocationDetailCreateResponse
import com.retrip.map.application.`in`.response.LocationDetailResponse
import com.retrip.map.application.`in`.response.LocationDetailUpdateResponse
import com.retrip.map.application.`in`.usecase.LocationDetailUseCase
import com.retrip.map.application.out.repository.LocationDetailQueryRepository
import com.retrip.map.application.out.repository.LocationDetailRepository
import com.retrip.map.domain.exception.LocationDetailNotFoundException
import com.retrip.map.domain.exception.common.RequireException
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@RequiredArgsConstructor
@Transactional
class LocationDetailService(
    val locationDetailRepository: LocationDetailRepository,
    val locationDetailQueryRepository: LocationDetailQueryRepository
) : LocationDetailUseCase {

    @Transactional(readOnly = true)
    override fun getLocationDetail(id: UUID?, page: Pageable): Page<LocationDetailResponse> {
       return locationDetailQueryRepository.findLocationDetails(id, page)
    }

    override fun createLocationDetail(request: LocationDetailCreateRequest): LocationDetailCreateResponse {
        val locationDetail = locationDetailRepository.save(request.to())
        return LocationDetailCreateResponse(
            locationDetail.id ?: throw LocationDetailNotFoundException(),
            locationDetail.name?.value ?: throw RequireException(),
            locationDetail.category?.value ?: throw RequireException(),
            locationDetail.description?.value,
            locationDetail.telephone,
            locationDetail.address?.address,
            locationDetail.address?.roadAddress,
            locationDetail.geoPoint?.latitude,
            locationDetail.geoPoint?.longitude,
        )
    }

    override fun updateLocationDetail(id: UUID, request: LocationDetailUpdateRequest): LocationDetailUpdateResponse {
        val location = locationDetailRepository.findByIdOrNull(id) ?: throw LocationDetailNotFoundException()
        location.update(
            request.name,
            request.category,
            request.description,
            request.telephone,
            request.address,
            request.roadAddress,
            request.latitude,
            request.longitude,
        )
        return LocationDetailUpdateResponse(
            location.id ?: throw LocationDetailNotFoundException(),
            location.name?.value ?: throw RequireException(),
            location.category?.value ?: throw RequireException(),
            location.description?.value,
            location.telephone,
            location.address?.address,
            location.address?.roadAddress,
            location.geoPoint?.latitude,
            location.geoPoint?.longitude,
        )
    }

    override fun deleteLocationDetail(locationDetailId: UUID) {
        locationDetailRepository.deleteById(locationDetailId)
    }
}

