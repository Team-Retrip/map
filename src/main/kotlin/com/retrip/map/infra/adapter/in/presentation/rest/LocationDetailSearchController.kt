package com.retrip.map.infra.adapter.`in`.presentation.rest

import com.retrip.map.application.`in`.response.LocationDetailSearchResponse
import com.retrip.map.application.`in`.usecase.LocationDetailSearchUseCase
import com.retrip.map.infra.adapter.`in`.presentation.common.ApiResponse
import io.swagger.v3.oas.annotations.media.Schema
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequiredArgsConstructor
@RequestMapping("search/location-details")
class LocationDetailSearchController(
    private val locationDetailSearchUseCase: LocationDetailSearchUseCase
) {

    @GetMapping("")
    @Schema(description = "장소 검색 엔진 조회")
    fun getLocation(
        @PageableDefault(size = 10, page = 0) page: Pageable,
        @RequestParam(name = "locationId", required = false) locationId: UUID?,
        @RequestParam(name = "searchText", required = false) searchText: String?
    ): ApiResponse<Page<LocationDetailSearchResponse>> {
        val result = locationDetailSearchUseCase.getDetailLocation(locationId, searchText, page)
        return ApiResponse.ok(result)
    }
}
