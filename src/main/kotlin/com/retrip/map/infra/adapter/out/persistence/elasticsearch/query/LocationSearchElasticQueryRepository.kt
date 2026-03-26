package com.retrip.map.infra.adapter.out.persistence.elasticsearch.query

import com.retrip.map.application.out.repository.LocationSearchQueryRepository
import com.retrip.map.infra.adapter.out.persistence.elasticsearch.convert.LocationQueryDocumentConvert.toDocument
import com.retrip.map.infra.adapter.out.persistence.elasticsearch.query.dto.LocationQueryDocument
import com.retrip.map.infra.adapter.out.search.elasticsearch.entity.LocationDocument
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import org.springframework.data.elasticsearch.client.elc.NativeQuery
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter


@Repository
class LocationSearchElasticQueryRepository(
    private val elasticsearchOperations: ElasticsearchOperations
) : LocationSearchQueryRepository {
    override fun findBySearchText(searchText: String?, pageable: Pageable): Page<LocationDocument> {
        val queryBuilder = NativeQuery.builder()
            .withPageable(pageable)
            .withSourceFilter(FetchSourceFilter(false, emptyArray(), arrayOf("*")))
            .withFields(
                "id",
                "name",
                "country",
                "latitude",
                "longitude",
                "createdAt",
                "editedAt",
                "searchText"
            )

        if (!searchText.isNullOrBlank()) {
            queryBuilder.withQuery { q ->
                q.match { m -> m.field("searchText").query(searchText) }
            }
        }

        val searchHits = elasticsearchOperations.search(
            queryBuilder.build(),
            LocationQueryDocument::class.java,
            IndexCoordinates.of("location")
        )

        val content = searchHits.searchHits.map { it.content.toDocument() }
        return PageImpl(content, pageable, searchHits.totalHits)
    }
}
