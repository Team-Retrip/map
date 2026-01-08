package com.retrip.map.application.`in`.response

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@Schema(description = "장소 상세 생성 응답")
data class LocationDetailCreateResponse(
    @Schema(description = "장소 상세 id")
    val id: UUID,
    @Schema(description = "장소 상세명")
    val name: String,
    @Schema(description = "장소 상세 카테고리")
    val category: String,
    @Schema(description = "장소 상세 설명")
    val description: String?,
    @Schema(description = "장소 상세 전화번호")
    val telephone: String?,
    @Schema(description = "장소 상세 주소")
    val address: String?,
    @Schema(description = "장소 상세 도로명 주소")
    val roadAddress: String?,
    @Schema(description = "장소 상세 위도")
    val latitude: Double?,
    @Schema(description = "장소 상세 경도")
    val longitude: Double?,
) {
}
