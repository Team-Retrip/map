package com.retrip.map.application.out.repository

import com.retrip.map.infra.adapter.out.search.elasticsearch.entity.LocationDocument
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface LocationElasticRepository: ElasticsearchRepository<LocationDocument, UUID> {
    fun findFirstByOrderByEditedAtDesc(): LocationDocument?
}
