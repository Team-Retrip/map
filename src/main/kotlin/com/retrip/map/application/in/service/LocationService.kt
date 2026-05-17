package com.retrip.map.application.`in`.service

import com.retrip.map.application.`in`.request.LocationCreateRequest
import com.retrip.map.application.`in`.request.LocationUpdateRequest
import com.retrip.map.application.`in`.response.LocationCreateResponse
import com.retrip.map.application.`in`.response.LocationResponse
import com.retrip.map.application.`in`.response.LocationUpdateResponse
import com.retrip.map.application.`in`.usecase.LocationUseCase
import com.retrip.map.application.out.repository.LocationQueryRepository
import com.retrip.map.application.out.repository.LocationRepository
import com.retrip.map.domain.exception.LocationDetailNotFoundException
import com.retrip.map.domain.exception.LocationDuplicateException
import com.retrip.map.domain.exception.LocationNotFoundException
import com.retrip.map.domain.exception.common.RequireException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class LocationService(
    val locationRepository: LocationRepository,
    val locationQueryRepository: LocationQueryRepository,
) : LocationUseCase {

    @Transactional(readOnly = true)
    override fun getLocation(id: UUID?, page: Pageable): Page<LocationResponse> {
        return locationQueryRepository.findLocations(id, page)
    }


    override fun createLocation(request: LocationCreateRequest): LocationCreateResponse {
        locationRepository.findFirstByNameValueAndCountryValue(request.name, request.country)
            ?.run { throw LocationDuplicateException() }

        val location = locationRepository.save(request.to())
        return LocationCreateResponse(
            location.id ?: throw LocationNotFoundException(),
            location.name?.value ?: throw RequireException(),
            location.country?.value ?: throw RequireException(),
            location.geoPoint?.latitude,
            location.geoPoint?.longitude,
        )
    }

    override fun updateLocation(id: UUID, request: LocationUpdateRequest): LocationUpdateResponse {
        locationRepository.findFirstByNameValueAndCountryValue(request.name, request.country)
            ?.run { throw LocationDuplicateException() }

        val location = locationRepository.findByIdOrNull(id) ?: throw LocationNotFoundException()
        location.update(
            request.name,
            request.country,
            request.latitude,
            request.longitude,
        )
        return LocationUpdateResponse(
            location.id ?: throw LocationDetailNotFoundException(),
            location.name?.value ?: throw RequireException(),
            location.country?.value ?: throw RequireException(),
            location.geoPoint?.latitude,
            location.geoPoint?.longitude,
        )
    }

    override fun deleteLocation(locationId: UUID) {
        locationRepository.deleteById(locationId)
    }
}

