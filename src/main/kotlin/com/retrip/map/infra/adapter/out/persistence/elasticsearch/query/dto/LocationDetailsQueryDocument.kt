package com.retrip.map.infra.adapter.out.persistence.elasticsearch.query.dto

import java.time.Instant

data class LocationDetailsQueryDocument(
    val id: List<String>? = emptyList(),
    val name: List<String>? = emptyList(),
    val category: List<String>? = emptyList(),
    val searchText: List<String>? = emptyList(), //검색시에만 사용
    val description: List<String>? = emptyList(),
    val address: List<String>? = emptyList(),
    val telephone: List<String>? = emptyList(),
    val roadAddress: List<String>? = emptyList(),
    val latitude: List<Double>? = emptyList(),
    val longitude: List<Double>? = emptyList(),
    val locationId: List<String>? = emptyList(),
    val type: List<String>? = emptyList(),
    val createdAt: List<Instant>? = emptyList(),
    val editedAt: List<Instant>? = emptyList(),
)
