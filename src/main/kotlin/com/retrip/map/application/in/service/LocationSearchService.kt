package com.retrip.map.application.`in`.service

import com.retrip.map.application.`in`.request.LocationCreateRequest
import com.retrip.map.application.`in`.request.LocationDetailCreateRequest
import com.retrip.map.application.`in`.request.LocationDetailUpdateRequest
import com.retrip.map.application.`in`.request.LocationUpdateRequest
import com.retrip.map.application.`in`.response.LocationCreateResponse
import com.retrip.map.application.`in`.response.LocationDetailCreateResponse
import com.retrip.map.application.`in`.response.LocationDetailResponse
import com.retrip.map.application.`in`.response.LocationDetailUpdateResponse
import com.retrip.map.application.`in`.response.LocationResponse
import com.retrip.map.application.`in`.response.LocationSearchResponse
import com.retrip.map.application.`in`.response.LocationUpdateResponse
import com.retrip.map.application.`in`.usecase.LocationDetailUseCase
import com.retrip.map.application.`in`.usecase.LocationSearchUseCase
import com.retrip.map.application.`in`.usecase.LocationUseCase
import com.retrip.map.application.out.repository.LocationDetailQueryRepository
import com.retrip.map.application.out.repository.LocationDetailRepository
import com.retrip.map.application.out.repository.LocationElasticRepository
import com.retrip.map.application.out.repository.LocationQueryRepository
import com.retrip.map.application.out.repository.LocationRepository
import com.retrip.map.domain.exception.LocationDetailNotFoundException
import com.retrip.map.domain.exception.LocationNotFoundException
import com.retrip.map.domain.exception.common.RequireException
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
class LocationSearchService(
    val locationElasticRepository: LocationElasticRepository
) : LocationSearchUseCase {

    @Transactional(readOnly = true)
    override fun getLocation(name: String?, page: Pageable): Page<LocationSearchResponse> {
        val locations = locationElasticRepository.findByNameContaining(name, page)
        return locations.map {
            LocationSearchResponse(
                id = it.id,
                name = it.name,
                latitude = it.latitude,
                longitude = it.longitude,
            )
        }
    }

}

