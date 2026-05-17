package com.retrip.map.application.`in`.service

import com.retrip.map.application.`in`.request.LocationDetailRecentSearchModel
import com.retrip.map.application.`in`.request.context.UserContext
import com.retrip.map.application.`in`.response.LocationDetailSearchResponse
import com.retrip.map.application.`in`.usecase.LocationDetailRecentSearchUseCase
import com.retrip.map.application.`in`.usecase.LocationDetailSearchUseCase
import com.retrip.map.application.out.repository.LocationDetailSearchQueryRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
@Transactional(readOnly = true)
class LocationDetailSearchService(
    private val locationDetailSearchQueryRepository: LocationDetailSearchQueryRepository,
    private val locationDetailRecentSearchUseCase: LocationDetailRecentSearchUseCase,
) : LocationDetailSearchUseCase {

    override fun getDetailLocation(locationId: UUID?, searchText: String?, page: Pageable, context: UserContext): Page<LocationDetailSearchResponse> {
        val locationDetails = locationDetailSearchQueryRepository.findByLocationIdAndSearchText(locationId, searchText, page)
        if (!searchText.isNullOrBlank() && context.memberId != null) {
            locationDetailRecentSearchUseCase.addLocationDetailRecentSearch(
                LocationDetailRecentSearchModel(
                    searchText = searchText,
                    memberId = context.memberId,
                    updateTime = LocalDateTime.now(),
                )
            )
        }
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
            )
        }
    }
}
