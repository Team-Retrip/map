package com.retrip.map.application.`in`.request

import com.retrip.map.domain.entity.LocationDetail
import io.swagger.v3.oas.annotations.media.Schema

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
    fun to(): LocationDetail {
        return LocationDetail.create(
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
