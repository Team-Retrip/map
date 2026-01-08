package com.retrip.map.application.`in`.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "장소 수정 Request")
data class LocationUpdateRequest(
    val name: String,
    val latitude: Double,
    val longitude: Double,
) {
}
