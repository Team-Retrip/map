package com.retrip.map.infra.adapter.out.persistence.elasticsearch.convert

import com.retrip.map.infra.adapter.out.persistence.elasticsearch.query.dto.LocationQueryDocument
import com.retrip.map.infra.adapter.out.search.elasticsearch.entity.LocationDocument
import java.util.UUID

object LocationQueryDocumentConvert {

    fun LocationQueryDocument.toDocument(): LocationDocument {
        return LocationDocument(
            id = this.id?.firstOrNull()?.let { UUID.fromString(it) } ?: throw IllegalArgumentException("Cannot find location query document id"),
            name = this.name?.firstOrNull() ?: throw IllegalArgumentException("Cannot find location query document name"),
            country = this.country?.firstOrNull() ?: throw IllegalArgumentException("Cannot find location query document country"),
            searchText = this.searchText?.firstOrNull() ?: throw IllegalArgumentException("Cannot find location query document searchText"),
            latitude = this.latitude?.firstOrNull() ?: throw IllegalArgumentException("Cannot find location query document latitude"),
            longitude = this.longitude?.firstOrNull() ?: throw IllegalArgumentException("Cannot find location query document longitude"),
            createdAt = this.createdAt?.firstOrNull() ?: throw IllegalArgumentException("Cannot find location query document createdAt"),
            editedAt = this.editedAt?.firstOrNull() ?: throw IllegalArgumentException("Cannot find location query document editedAt"),
        )
    }
}
