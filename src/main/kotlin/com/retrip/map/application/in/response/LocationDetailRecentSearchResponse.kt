package com.retrip.map.application.`in`.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "상세 장소 최근 조회 Response")
data class LocationDetailRecentSearchResponse(
    @Schema(description = "검색어")
    val searchTexts: List<String>? = null,
)
