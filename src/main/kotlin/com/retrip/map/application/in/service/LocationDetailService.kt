package com.retrip.map.application.`in`.service

import com.retrip.map.application.`in`.request.LocationDetailCreateRequest
import com.retrip.map.application.`in`.request.LocationDetailUpdateRequest
import com.retrip.map.application.`in`.response.LocationDetailCreateResponse
import com.retrip.map.application.`in`.response.LocationDetailResponse
import com.retrip.map.application.`in`.response.LocationDetailUpdateResponse
import com.retrip.map.application.`in`.usecase.LocationDetailUseCase
import com.retrip.map.application.out.repository.LocationDetailElasticRepository
import com.retrip.map.application.out.repository.LocationDetailQueryRepository
import com.retrip.map.application.out.repository.LocationDetailRepository
import com.retrip.map.application.out.repository.LocationRepository
import com.retrip.map.domain.exception.LocationDetailDuplicateException
import com.retrip.map.domain.exception.LocationDetailNotFoundException
import com.retrip.map.domain.exception.LocationNotFoundException
import com.retrip.map.domain.exception.common.RequireException
import com.retrip.map.infra.adapter.out.search.elasticsearch.entity.LocationDetailDocument
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@RequiredArgsConstructor
@Transactional
class LocationDetailService(
    val locationRepository: LocationRepository,
    val locationDetailRepository: LocationDetailRepository,
    val locationDetailQueryRepository: LocationDetailQueryRepository,
    val locationDetailElasticRepository: LocationDetailElasticRepository,
) : LocationDetailUseCase {

    @Transactional(readOnly = true)
    override fun getLocationDetail(locationId: UUID, id: UUID?, page: Pageable): Page<LocationDetailResponse> {
        return locationDetailQueryRepository.findLocationDetails(locationId, id, page)
    }

    override fun createLocationDetail(locationId: UUID, request: LocationDetailCreateRequest): LocationDetailCreateResponse {
        val location = locationRepository.findByIdOrNull(locationId) ?: throw LocationNotFoundException()
        val isDuplicate =
            locationDetailRepository.findByNameValueAndLocationId(request.name, locationId)
                ?.run { throw LocationDetailDuplicateException() }

        val locationDetail = locationDetailRepository.save(request.to(location))
        locationDetailElasticRepository.save(LocationDetailDocument.of(locationDetail))
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

    override fun updateLocationDetail(locationId: UUID, id: UUID, request: LocationDetailUpdateRequest): LocationDetailUpdateResponse {
        val location = locationRepository.findByIdOrNull(locationId) ?: throw LocationNotFoundException()
        val isDuplicate =
            locationDetailRepository.findByNameValueAndLocationId(request.name, locationId)
                ?.run { throw LocationDetailDuplicateException() }
        val locationDetails = locationDetailRepository.findByIdOrNull(id) ?: throw LocationDetailNotFoundException()
        locationDetails.update(
            location,
            request.name,
            request.category,
            request.description,
            request.telephone,
            request.address,
            request.roadAddress,
            request.latitude,
            request.longitude,
        )
        locationDetailElasticRepository.deleteById(id)
        locationDetailElasticRepository.save(LocationDetailDocument.of(locationDetails))
        return LocationDetailUpdateResponse(
            locationDetails.id ?: throw LocationDetailNotFoundException(),
            locationDetails.name?.value ?: throw RequireException(),
            locationDetails.category?.value ?: throw RequireException(),
            locationDetails.description?.value,
            locationDetails.telephone,
            locationDetails.address?.address,
            locationDetails.address?.roadAddress,
            locationDetails.geoPoint?.latitude,
            locationDetails.geoPoint?.longitude,
        )
    }

    override fun deleteLocationDetail(locationDetailId: UUID) {
        locationDetailRepository.deleteById(locationDetailId)
        locationDetailElasticRepository.deleteById(locationDetailId)

    }
}

