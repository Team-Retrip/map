package com.retrip.map.infra.adapter.out.search.elasticsearch.entity

import com.retrip.map.domain.entity.LocationDetail
import com.retrip.map.domain.exception.LocationDetailNotFoundException
import com.retrip.map.domain.exception.common.RequireException
import jakarta.persistence.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import org.springframework.data.elasticsearch.annotations.Mapping
import org.springframework.data.elasticsearch.annotations.Setting
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Document(indexName = "location-details")
@Setting(settingPath = "elasticsearch/settings/setting.json")
@Mapping(mappingPath = "elasticsearch/mappings/mapping-location-detail.json")
data class LocationDetailDocument(

    @Id
    val id: UUID,
    @Field(type = FieldType.Text, analyzer = "korean")
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
    val createdAt: Long? = null,
    @Field(type = FieldType.Date)
    val editedAt: Long? = null,
) {
    companion object {
        fun of(locationDetail: LocationDetail): LocationDetailDocument {
            return LocationDetailDocument(
                locationDetail.id ?: throw LocationDetailNotFoundException(),
                locationDetail.name?.value ?: throw RequireException(),
                locationDetail.category?.value ?: throw RequireException(),
                locationDetail.description?.value,
                locationDetail.telephone,
                locationDetail.address?.address,
                locationDetail.address?.roadAddress,
                locationDetail.geoPoint?.latitude,
                locationDetail.geoPoint?.longitude,
                locationDetail.createdAt?.toInstant(ZoneOffset.UTC)?.toEpochMilli(),
                locationDetail.editedAt?.toInstant(ZoneOffset.UTC)?.toEpochMilli()
            )
        }
    }

}
