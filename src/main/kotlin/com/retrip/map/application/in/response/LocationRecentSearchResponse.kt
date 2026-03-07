package com.retrip.map.application.`in`.response

import com.retrip.map.domain.vo.LocationCountry
import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@Schema(description = "장소 최근 조회 Response")
data class LocationRecentSearchResponse(
    @Schema(description = "검색어")
    val searchTexts: List<String>? = null,
) {
}
