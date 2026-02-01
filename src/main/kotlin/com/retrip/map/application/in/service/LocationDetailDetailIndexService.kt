package com.retrip.map.application.`in`.service

import com.retrip.map.application.`in`.usecase.LocationDetailIndexUseCase
import com.retrip.map.application.out.repository.LocationDetailElasticRepository
import com.retrip.map.infra.adapter.out.search.elasticsearch.entity.LocationDetailDocument
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LocationDetailDetailIndexService(
    private val locationDetailElasticRepository: LocationDetailElasticRepository
) : LocationDetailIndexUseCase {
    override fun indexLocationDetailDocuments(documents: List<LocationDetailDocument>?) {
        documents?.let { locationDetailElasticRepository.saveAll(it) }
    }
}
