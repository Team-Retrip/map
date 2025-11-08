package com.retrip.map.infra.adapter.out.search.elasticsearch.entity

import com.retrip.map.domain.entity.Location
import com.retrip.map.domain.exception.LocationNotFoundException
import com.retrip.map.domain.exception.common.RequireException
import jakarta.persistence.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.time.LocalDateTime
import java.util.*

@Document(indexName = "locations")
data class LocationDocument(
    @Id
    val id: UUID,
    @Field(type = FieldType.Text)
    val name: String,
    @Field(type = FieldType.Keyword)
    val category: String,
    @Field(type = FieldType.Keyword)
    val description: String?,
    @Field(type = FieldType.Keyword)
    val telephone: String?,
    @Field(type = FieldType.Keyword)
    val address: String?,
    @Field(type = FieldType.Keyword)
    val roadAddress: String?,
    @Field(type = FieldType.Double)
    val latitude: Double?,
    @Field(type = FieldType.Double)
    val longitude: Double?,
    @Field(type = FieldType.Date)
    val createdAt: LocalDateTime?,
    @Field(type = FieldType.Date)
    val editedAt: LocalDateTime?,
) {
    companion object {
        fun of(location: Location): LocationDocument {
            return LocationDocument(
                location.id ?: throw LocationNotFoundException(),
                location.name?.value ?: throw RequireException(),
                location.category?.value ?: throw RequireException(),
                location.description?.value,
                location.telephone,
                location.address?.address,
                location.address?.roadAddress,
                location.geoPoint?.latitude,
                location.geoPoint?.longitude,
                location.createdAt,
                location.editedAt
            )
        }
    }

}
