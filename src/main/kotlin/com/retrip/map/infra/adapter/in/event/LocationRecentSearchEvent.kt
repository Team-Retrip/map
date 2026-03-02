package com.retrip.map.infra.adapter.`in`.event

import com.retrip.map.application.`in`.request.LocationRecentSearchModel
import com.retrip.map.application.`in`.usecase.LocationRecentSearchUseCase
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class LocationRecentSearchEvent(
    private val locationRecentSearchUseCase: LocationRecentSearchUseCase
) {
    @Async
    @EventListener
    @Transactional
    fun locationRecentSearchEvent(locationRecentSearchModel: LocationRecentSearchModel) {
        locationRecentSearchUseCase.addLocationRecentSearch(locationRecentSearchModel)
    }
}
