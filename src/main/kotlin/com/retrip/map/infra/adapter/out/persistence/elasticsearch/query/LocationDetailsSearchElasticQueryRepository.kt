package com.retrip.map.infra.adapter.out.persistence.elasticsearch.query

import com.retrip.map.application.out.repository.LocationDetailSearchQueryRepository
import com.retrip.map.application.out.repository.LocationSearchQueryRepository
import com.retrip.map.infra.adapter.out.persistence.elasticsearch.convert.LocationDetailsQueryDocumentConvert.toDocument
import com.retrip.map.infra.adapter.out.persistence.elasticsearch.convert.LocationQueryDocumentConvert.toDocument
import com.retrip.map.infra.adapter.out.persistence.elasticsearch.query.dto.LocationDetailsQueryDocument
import com.retrip.map.infra.adapter.out.persistence.elasticsearch.query.dto.LocationQueryDocument
import com.retrip.map.infra.adapter.out.search.elasticsearch.entity.LocationDetailDocument
import com.retrip.map.infra.adapter.out.search.elasticsearch.entity.LocationDocument
import jakarta.persistence.Id
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import org.springframework.stereotype.Repository
import org.springframework.data.elasticsearch.client.elc.NativeQuery
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter
import java.time.Instant
import java.util.UUID


@Repository
class LocationDetailsSearchElasticQueryRepository(
    private val elasticsearchOperations: ElasticsearchOperations
) : LocationDetailSearchQueryRepository {
    override fun findByLocationIdAndSearchText(locationId: UUID?, searchText: String?, pageable: Pageable): Page<LocationDetailDocument> {
        val queryBuilder = NativeQuery.builder()
            .withPageable(pageable)
            .withSourceFilter(FetchSourceFilter(false, emptyArray(), arrayOf("*")))
            .withFields(
                "id",
                "name",
                "searchText",
                "category",
                "description",
                "telephone",
                "address",
                "roadAddress",
                "latitude",
                "longitude",
                "locationId",
                "createdAt",
                "editedAt",
            )
        if (!searchText.isNullOrBlank()) {
            queryBuilder.withQuery { q ->
                q.match { m -> m.field("searchText").query(searchText) }
            }
        }
        locationId?.run {
            queryBuilder.withFilter { f ->
                f.term { t -> t.field("locationId").value(this.toString()) }
            }
        }

        val searchHits = elasticsearchOperations.search(
            queryBuilder.build(),
            LocationDetailsQueryDocument::class.java,
            IndexCoordinates.of("location-details")
        )

        val content = searchHits.searchHits.map { it.content.toDocument() }
        return PageImpl(content, pageable, searchHits.totalHits)
    }
}
