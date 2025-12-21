package com.retrip.map.application.`in`.request

import com.retrip.map.domain.entity.Location
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "장소 생성 Request")
data class LocationCreateRequest(
    val name: String,
    val latitude: Double,
    val longitude: Double,
) {
    fun to(): Location {
        return Location.create(
            name,
            latitude,
            longitude
        )
    }
}
