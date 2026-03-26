package com.retrip.map.application.out.repository

import com.retrip.map.infra.adapter.out.search.elasticsearch.entity.LocationDetailDocument
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface LocationDetailSearchQueryRepository {
    fun findByLocationIdAndSearchText(locationId: UUID?, searchText: String?, pageable: Pageable): Page<LocationDetailDocument>
}
