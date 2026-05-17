package com.retrip.map.application.`in`.usecase

import com.retrip.map.application.`in`.request.LocationDetailRecentSearchModel
import com.retrip.map.application.`in`.request.context.UserContext
import com.retrip.map.application.`in`.response.LocationDetailRecentSearchResponse

interface LocationDetailRecentSearchUseCase {
    fun getRecentLocationDetail(context: UserContext): LocationDetailRecentSearchResponse
    fun addLocationDetailRecentSearch(model: LocationDetailRecentSearchModel)
    fun deleteRecentLocationDetailsByKeyword(context: UserContext, keyword: String?)
}
