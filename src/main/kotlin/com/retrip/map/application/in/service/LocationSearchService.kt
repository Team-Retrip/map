package com.retrip.map.application.`in`.service

import com.retrip.map.application.`in`.request.LocationRecentSearchModel
import com.retrip.map.application.`in`.request.context.UserContext
import com.retrip.map.application.`in`.response.LocationSearchResponse
import com.retrip.map.application.`in`.usecase.LocationSearchUseCase
import com.retrip.map.application.out.repository.LocationSearchHistoryRepository
import com.retrip.map.application.out.repository.LocationSearchQueryRepository
import com.retrip.map.domain.entity.LocationSearchHistory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class LocationSearchService(
    val locationSearchQueryRepository: LocationSearchQueryRepository,
    val locationSearchHistoryRepository: LocationSearchHistoryRepository,
    val eventPublisher: ApplicationEventPublisher,
) : LocationSearchUseCase {

    @Transactional
    override fun getLocation(searchText: String?, page: Pageable, context: UserContext): Page<LocationSearchResponse> {
        val locations = locationSearchQueryRepository.findBySearchText(searchText, page)
        if (!searchText.isNullOrBlank()) {
            val memberId = context.memberId
            val locationSearchHistory =
                locationSearchHistoryRepository.save(LocationSearchHistory.create(searchText, memberId))
            eventPublisher.publishEvent(
                LocationRecentSearchModel(
                    searchText = searchText,
                    memberId = memberId,
                    updateTime = locationSearchHistory.createdAt ?: LocalDateTime.now(),
                )
            )
        }
        return locations.map {
            LocationSearchResponse(
                id = it.id,
                name = it.name,
                country = it.country,
                latitude = it.latitude,
                longitude = it.longitude,
            )
        }
    }
}

