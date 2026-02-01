package com.retrip.map.application.`in`.usecase

import com.retrip.map.application.`in`.response.LocationDetailSearchResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.UUID

interface LocationDetailSearchUseCase {
    fun getDetailLocation(locationId: UUID?,searchText: String?, page: Pageable): Page<LocationDetailSearchResponse>
}
