package com.retrip.map.application.`in`.service

import com.retrip.map.application.`in`.usecase.LocationIndexUseCase
import com.retrip.map.application.out.repository.LocationElasticRepository
import com.retrip.map.infra.adapter.out.search.elasticsearch.entity.LocationDocument
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LocationIndexService(
    private val locationElasticRepository: LocationElasticRepository
) : LocationIndexUseCase {
    override fun indexLocationDocuments(documents: List<LocationDocument>?) {
        documents?.let { locationElasticRepository.saveAll(it) }
    }
}
