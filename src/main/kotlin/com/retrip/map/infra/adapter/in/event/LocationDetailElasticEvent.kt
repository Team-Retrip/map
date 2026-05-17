package com.retrip.map.infra.adapter.`in`.event

import com.retrip.map.infra.adapter.out.search.elasticsearch.entity.LocationDetailDocument
import java.util.UUID

sealed class LocationDetailElasticEvent {
    data class Created(val document: LocationDetailDocument) : LocationDetailElasticEvent()
    data class Updated(val id: UUID, val document: LocationDetailDocument) : LocationDetailElasticEvent()
    data class Deleted(val id: UUID) : LocationDetailElasticEvent()
}
