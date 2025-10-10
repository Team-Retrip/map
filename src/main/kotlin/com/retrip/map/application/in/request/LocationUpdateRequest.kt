package com.retrip.map.application.`in`.request

import com.retrip.map.domain.entity.Location
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "장소 수정 Request")
data class LocationUpdateRequest(
    val name: String,
    val category: String,
    val description: String?,
    val telephone: String?,
    val address: String?,
    val roadAddress: String?,
    val latitude: Double,
    val longitude: Double,
) {
}
