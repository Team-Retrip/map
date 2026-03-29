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
            searchText = this.searchText?.firstOrNull() ?: throw IllegalArgumentException("Cannot find location query document searchText"),
            latitude = this.latitude?.firstOrNull() ?: throw IllegalArgumentException("Cannot find location query document latitude"),
            longitude = this.longitude?.firstOrNull() ?: throw IllegalArgumentException("Cannot find location query document longitude"),
            createdAt = this.createdAt?.firstOrNull() ?: throw IllegalArgumentException("Cannot find location query document createdAt"),
            editedAt = this.editedAt?.firstOrNull() ?: throw IllegalArgumentException("Cannot find location query document editedAt"),
            roadAddress = this.roadAddress?.firstOrNull() ?: throw IllegalArgumentException("Cannot find location query document roadAddress"),
            telephone = this.telephone?.firstOrNull() ?: throw IllegalArgumentException("Cannot find location query document telephone"),
            address = this.address?.firstOrNull() ?: throw IllegalArgumentException("Cannot find location query document address"),
            description = this.description?.firstOrNull() ?: throw IllegalArgumentException("Cannot find location query document description"),
            locationId =  this.locationId?.firstOrNull()?.let { UUID.fromString(it) } ?: throw IllegalArgumentException("Cannot find location query document locationId"),
            type = this.type?.firstOrNull() ?: throw IllegalArgumentException("Cannot find location query document type"),
        )

    }
}
