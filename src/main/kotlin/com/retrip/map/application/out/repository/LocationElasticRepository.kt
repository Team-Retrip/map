package com.retrip.map.application.out.repository

import com.retrip.map.infra.adapter.out.search.elasticsearch.entity.LocationDocument
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface LocationElasticRepository: ElasticsearchRepository<LocationDocument, UUID> {
    // 이름 포함 검색 + 페이징
    fun findByNameContaining(name: String?, pageable: Pageable): Page<LocationDocument>
}
