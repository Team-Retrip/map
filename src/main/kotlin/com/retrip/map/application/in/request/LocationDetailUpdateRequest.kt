package com.retrip.map.application.`in`.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "장소 상세 수정 Request")
data class LocationDetailUpdateRequest(
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
}
