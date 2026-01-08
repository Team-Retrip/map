package com.retrip.map.application.`in`.usecase

import com.retrip.map.application.`in`.request.LocationCreateRequest
import com.retrip.map.application.`in`.request.LocationUpdateRequest
import com.retrip.map.application.`in`.response.LocationCreateResponse
import com.retrip.map.application.`in`.response.LocationResponse
import com.retrip.map.application.`in`.response.LocationSearchResponse
import com.retrip.map.application.`in`.response.LocationUpdateResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface LocationSearchUseCase {
    fun getLocation(name: String?, page: Pageable): Page<LocationSearchResponse>
}
