package com.retrip.map.application.out.repository

import com.retrip.map.infra.adapter.out.search.elasticsearch.entity.LocationDetailDocument
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface LocationDetailElasticRepository: ElasticsearchRepository<LocationDetailDocument, UUID> {
    fun findFirstByOrderByEditedAtDesc(): LocationDetailDocument?
}
