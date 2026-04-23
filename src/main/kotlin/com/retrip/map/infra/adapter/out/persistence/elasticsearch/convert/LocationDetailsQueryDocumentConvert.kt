package com.retrip.map.infra.adapter.out.persistence.elasticsearch.convert

import com.retrip.map.infra.adapter.out.persistence.elasticsearch.query.dto.LocationDetailsQueryDocument
import com.retrip.map.infra.adapter.out.persistence.elasticsearch.query.dto.LocationQueryDocument
import com.retrip.map.infra.adapter.out.search.elasticsearch.entity.LocationDetailDocument
import com.retrip.map.infra.adapter.out.search.elasticsearch.entity.LocationDocument
import java.util.UUID

object LocationDetailsQueryDocumentConvert {

    fun LocationDetailsQueryDocument.toDocument(): LocationDetailDocument {
        return LocationDetailDocument(
            id = this.id?.firstOrNull()?.let { UUID.fromString(it) } ?: throw IllegalArgumentException("Cannot find location query document id"),
            name = this.name?.firstOrNull() ?: throw IllegalArgumentException("Cannot find location query document name"),
            category = this.category?.firstOrNull() ?: throw IllegalArgumentException("Cannot find location query document category"),
            searchText = this.searchText?.firstOrNull(),
            latitude = this.latitude?.firstOrNull(),
            longitude = this.longitude?.firstOrNull(),
            createdAt = this.createdAt?.firstOrNull(),
            editedAt = this.editedAt?.firstOrNull(),
            roadAddress = this.roadAddress?.firstOrNull(),
            telephone = this.telephone?.firstOrNull(),
            address = this.address?.firstOrNull(),
            description = this.description?.firstOrNull(),
            locationId = this.locationId?.firstOrNull()?.let { UUID.fromString(it) } ?: throw IllegalArgumentException("Cannot find location query document locationId"),
        )
    }
}
