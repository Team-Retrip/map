package com.retrip.map.infra.adapter.out.search.elasticsearch.entity

import com.retrip.map.domain.entity.Location
import com.retrip.map.domain.exception.LocationNotFoundException
import com.retrip.map.domain.exception.common.RequireException
import jakarta.persistence.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import org.springframework.data.elasticsearch.annotations.Mapping
import org.springframework.data.elasticsearch.annotations.Setting
import java.time.Instant
import java.time.ZoneOffset
import java.util.*

@Document(indexName = "location")
@Setting(settingPath = "elasticsearch/settings/setting.json")
@Mapping(mappingPath = "elasticsearch/mappings/mapping-location.json")
data class LocationDocument(
    @Id
    val id: UUID,
    @Field(type = FieldType.Text, analyzer = "korean", copyTo = ["searchText"])
    val name: String,
    @Field(type = FieldType.Text, analyzer = "korean", copyTo = ["searchText"])
    val country: String,
    @Field(type = FieldType.Text, analyzer = "korean")
    val searchText: String? = null, //검색시에만 사용
    @Field(type = FieldType.Double)
    val latitude: Double?,
    @Field(type = FieldType.Double)
    val longitude: Double?,
    @Field(type = FieldType.Date)
    val createdAt: Instant?,
    @Field(type = FieldType.Date)
    val editedAt: Instant?,
) {
    companion object {
        fun of(location: Location): LocationDocument {
            return LocationDocument(
                location.id ?: throw LocationNotFoundException(),
                location.name?.value ?: throw RequireException(),
                location.country?.value ?: throw RequireException(),
                null,
                location.geoPoint?.latitude,
                location.geoPoint?.longitude,
                location.createdAt?.toInstant(ZoneOffset.UTC),
                    location.editedAt?.toInstant(ZoneOffset.UTC)
                //location.createdAt?.toInstant(ZoneOffset.UTC)?.toEpochMilli(),
                //location.editedAt?.toInstant(ZoneOffset.UTC)?.toEpochMilli()
            )
        }
    }

}
