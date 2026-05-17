package com.retrip.map.infra.adapter.`in`.event

import com.retrip.map.infra.adapter.out.search.elasticsearch.entity.LocationDocument
import java.util.UUID

sealed class LocationElasticEvent {
    data class Created(val document: LocationDocument) : LocationElasticEvent()
    data class Updated(val id: UUID, val document: LocationDocument) : LocationElasticEvent()
    data class Deleted(val id: UUID) : LocationElasticEvent()
}
