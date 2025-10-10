package com.retrip.map.application.`in`.response

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

data class LocationUpdateResponse(
    @Schema(description = "장소 id")
    val id: UUID,
    @Schema(description = "장소명")
    val name: String,
    @Schema(description = "장소 카테고리")
    val category: String,
    @Schema(description = "장소 설명")
    val description: String?,
    @Schema(description = "장소 전화번호")
    val telephone: String?,
    @Schema(description = "장소 주소")
    val address: String?,
    @Schema(description = "장소 도로명 주소")
    val roadAddress: String?,
    @Schema(description = "장소 위도")
    val latitude: Double?,
    @Schema(description = "장소 경도")
    val longitude: Double?,
) {
}
