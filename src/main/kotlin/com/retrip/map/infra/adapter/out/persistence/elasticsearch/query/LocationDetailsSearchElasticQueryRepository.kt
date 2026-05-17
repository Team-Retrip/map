package com.retrip.map.infra.adapter.out.persistence.elasticsearch.query

import com.retrip.map.application.out.repository.LocationDetailSearchQueryRepository
import com.retrip.map.infra.adapter.out.search.elasticsearch.entity.LocationDetailDocument
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import org.springframework.data.elasticsearch.client.elc.NativeQuery
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import java.util.UUID


@Repository
class LocationDetailsSearchElasticQueryRepository(
    private val elasticsearchOperations: ElasticsearchOperations
) : LocationDetailSearchQueryRepository {
    override fun findByLocationIdAndSearchText(locationId: UUID?, searchText: String?, pageable: Pageable): Page<LocationDetailDocument> {
        val queryBuilder = NativeQuery.builder()
            .withPageable(pageable)

        if (!searchText.isNullOrBlank()) {
            queryBuilder.withQuery { q ->
                q.match { m -> m.field("searchText").query(searchText) }
            }
        }
        locationId?.let {
            queryBuilder.withFilter { f ->
                f.term { t -> t.field("locationId").value(it.toString()) }
            }
        }

        val searchHits = elasticsearchOperations.search(
            queryBuilder.build(),
            LocationDetailDocument::class.java,
            IndexCoordinates.of("location-details")
        )

        return PageImpl(searchHits.searchHits.map { it.content }, pageable, searchHits.totalHits)
    }
}
