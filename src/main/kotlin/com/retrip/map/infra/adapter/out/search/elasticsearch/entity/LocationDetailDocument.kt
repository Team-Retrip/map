package com.retrip.map.infra.adapter.out.search.elasticsearch.entity

import com.retrip.map.domain.entity.LocationDetail
import com.retrip.map.domain.exception.LocationDetailNotFoundException
import com.retrip.map.domain.exception.LocationNotFoundException
import com.retrip.map.domain.exception.common.RequireException
import jakarta.persistence.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import org.springframework.data.elasticsearch.annotations.Mapping
import org.springframework.data.elasticsearch.annotations.Setting
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Document(indexName = "location-details")
@Setting(settingPath = "elasticsearch/settings/setting.json")
@Mapping(mappingPath = "elasticsearch/mappings/mapping-location-detail.json")
data class LocationDetailDocument(
    @Id
    val id: UUID,
    @Field(type = FieldType.Text, analyzer = "korean", copyTo = ["searchText"])
    val name: String,
    @Field(type = FieldType.Text, analyzer = "korean")
    val searchText: String? = null, //검색시에만 사용
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
    @Field(type = FieldType.Keyword)
    val type: String?,
    @Field(type = FieldType.Keyword)
    val locationId: UUID,
    @Field(type = FieldType.Date)
    val createdAt: Instant? = null,
    @Field(type = FieldType.Date)
    val editedAt: Instant? = null,
) {
    companion object {
        fun of(locationDetail: LocationDetail): LocationDetailDocument {
            return LocationDetailDocument(
                locationDetail.id ?: throw LocationDetailNotFoundException(),
                locationDetail.name?.value ?: throw RequireException(),
                searchText = null,
                locationDetail.category?.value ?: throw RequireException(),
                locationDetail.description?.value,
                locationDetail.telephone,
                locationDetail.address?.address,
                locationDetail.address?.roadAddress,
                locationDetail.geoPoint?.latitude,
                locationDetail.geoPoint?.longitude,
                locationDetail.type?.name,
                locationDetail.location?.id ?: throw LocationNotFoundException(),
                locationDetail.createdAt?.toInstant(ZoneOffset.UTC),
                locationDetail.editedAt?.toInstant(ZoneOffset.UTC)
            )
        }
    }

}
