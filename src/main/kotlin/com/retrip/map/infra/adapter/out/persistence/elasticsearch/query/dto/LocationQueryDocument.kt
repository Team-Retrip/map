package com.retrip.map.infra.adapter.out.persistence.elasticsearch.query.dto

import java.time.Instant

data class LocationQueryDocument(
    val id: List<String>? = emptyList(),
    val name: List<String>? = emptyList(),
    val country: List<String>? = emptyList(),
    val searchText: List<String>? = emptyList(), //검색시에만 사용
    val latitude: List<Double>? = emptyList(),
    val longitude: List<Double>? = emptyList(),
    val createdAt: List<Instant>? = emptyList(),
    val editedAt: List<Instant>? = emptyList(),
) {
}
