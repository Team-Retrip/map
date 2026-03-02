package com.retrip.map.application.`in`.usecase

import com.retrip.map.application.`in`.request.LocationRecentSearchModel
import com.retrip.map.application.`in`.request.context.UserContext
import com.retrip.map.application.`in`.response.LocationRecentSearchResponse
import com.retrip.map.application.`in`.response.LocationSearchResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface LocationRecentSearchUseCase {
    fun getRecentLocation( context: UserContext) : LocationRecentSearchResponse?
    fun addLocationRecentSearch(locationRecentSearchModel: LocationRecentSearchModel)
    fun delectRecentLocationsByKeyword(context: UserContext, keyword: String?)
}
