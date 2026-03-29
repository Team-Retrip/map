package com.retrip.map.application.`in`.request

import com.retrip.map.domain.entity.Location
import com.retrip.map.domain.entity.LocationDetail
import com.retrip.map.domain.vo.LocationDetailType
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
    val type: Type
) {

    enum class Type {
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
            category,
            description,
            telephone,
            address,
            roadAddress,
            latitude,
            longitude,
            LocationDetailType.entries.firstOrNull { it.name == type.name } ?: LocationDetailType.UNKNOWN
        )
    }
}
