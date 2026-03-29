package com.retrip.map.application.`in`.request

import com.retrip.map.domain.entity.Location
import com.retrip.map.domain.entity.LocationDetail
import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@Schema(description = "장소 상세 생성 Request")
data class LocationDetailCreateRequest(
    val name: String,
    val category: CreateLocationDetailType,
    val description: String?,
    val telephone: String?,
    val address: String?,
    val roadAddress: String?,
    val latitude: Double,
    val longitude: Double,
) {
    enum class CreateLocationDetailType {
        RESTAURANT,
        CAFE,
        SHOPPING,
        LEISURE,
        LANDMARK,
        PARK,
        ZOO,
        SEA,
        TRANSPORT,
        ACCOMMODATION,
        FLIGHT,
        ETC
    }
    fun to(location: Location): LocationDetail {
        return LocationDetail.create(
            location,
            name,
            category.name,
            description,
            telephone,
            address,
            roadAddress,
            latitude,
            longitude
        )
    }
}
