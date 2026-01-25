package com.retrip.map.application.`in`.response

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@Schema(description = "장소 조회 Response")
data class LocationResponse(
    @Schema(description = "장소 id")
    val id: UUID,
    @Schema(description = "장소명")
    val name: String,
    @Schema(description = "국가")
    val country: String,
    @Schema(description = "장소 위도")
    val latitude: Double?,
    @Schema(description = "장소 경도")
    val longitude: Double?,
) {
}
