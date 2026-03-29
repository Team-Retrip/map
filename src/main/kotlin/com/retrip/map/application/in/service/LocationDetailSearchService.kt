package com.retrip.map.application.`in`.service

import com.retrip.map.application.`in`.response.LocationDetailSearchResponse
import com.retrip.map.application.`in`.usecase.LocationDetailSearchUseCase
import com.retrip.map.application.out.repository.LocationDetailElasticRepository
import com.retrip.map.application.out.repository.LocationDetailSearchQueryRepository
import com.retrip.map.application.out.repository.LocationSearchQueryRepository
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class LocationDetailSearchService(
    private val locationDetailSearchQueryRepository: LocationDetailSearchQueryRepository,
) : LocationDetailSearchUseCase {

    override fun getDetailLocation(locationId: UUID?, searchText: String?, page: Pageable): Page<LocationDetailSearchResponse> {
        val locationDetails = locationDetailSearchQueryRepository.findByLocationIdAndSearchText(locationId, searchText, page)
        return locationDetails.map {
            LocationDetailSearchResponse(
                id = it.id,
                locationId = it.locationId,
                name = it.name,
                category = it.category,
                description = it.description,
                telephone = it.telephone,
                address = it.address,
                roadAddress = it.roadAddress,
                latitude = it.latitude,
                longitude = it.longitude,
                type = it.type,
            )
        }
    }
}

