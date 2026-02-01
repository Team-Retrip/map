package com.retrip.map.application.out.repository

import com.retrip.map.application.`in`.response.LocationDetailResponse
import com.retrip.map.infra.adapter.out.search.elasticsearch.entity.LocationDetailDocument
import com.retrip.map.infra.adapter.out.search.elasticsearch.entity.LocationDocument
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface LocationDetailElasticRepository: ElasticsearchRepository<LocationDetailDocument, UUID> {
    fun findFirstByOrderByEditedAtDesc(): LocationDetailDocument?
    fun findByLocationIdAndSearchText(locationId: UUID?, name: String?, pageable: Pageable): Page<LocationDetailDocument>

}
