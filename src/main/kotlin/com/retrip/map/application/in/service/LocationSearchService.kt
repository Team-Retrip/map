package com.retrip.map.application.`in`.service

import com.retrip.map.application.`in`.response.LocationSearchResponse
import com.retrip.map.application.`in`.usecase.LocationSearchUseCase
import com.retrip.map.application.out.repository.LocationElasticRepository
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
@Transactional
class LocationSearchService(
    val locationElasticRepository: LocationElasticRepository
) : LocationSearchUseCase {

    @Transactional(readOnly = true)
    override fun getLocation(searchText: String?, page: Pageable): Page<LocationSearchResponse> {
        val locations = locationElasticRepository.findBySearchTextContaining(searchText, page)
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

