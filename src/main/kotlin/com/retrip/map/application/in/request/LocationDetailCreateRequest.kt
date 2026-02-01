package com.retrip.map.application.`in`.request

import com.retrip.map.domain.entity.Location
import com.retrip.map.domain.entity.LocationDetail
import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@Schema(description = "장소 상세 생성 Request")
data class LocationDetailCreateRequest(
    val name: String,
    val category: String,
    val description: String?,
    val telephone: String?,
    val address: String?,
    val roadAddress: String?,
    val latitude: Double,
    val longitude: Double,
) {
    fun to(location: Location): LocationDetail {
        return LocationDetail.create(
            location,
            name,
            category,
            description,
            telephone,
            address,
            roadAddress,
            latitude,
            longitude
        )
    }
}
