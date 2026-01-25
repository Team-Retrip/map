package com.retrip.map.infra.adapter.`in`.presentation

import com.retrip.map.application.`in`.response.LocationSearchResponse
import com.retrip.map.application.`in`.usecase.LocationSearchUseCase
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/location-search")
class LocationSearchController(
    private val locationSearchUseCase: LocationSearchUseCase
) {

    @GetMapping("")
    @Schema(description = "장소 검색 엔진 조회")
    fun getLocation(
        @PageableDefault(size = 10, page = 0) page: Pageable,
        @RequestParam(name = "searchText", required = false) searchText: String?
    ): ApiResponse<Page<LocationSearchResponse>> {
        val result = locationSearchUseCase.getLocation(searchText, page)
        return ApiResponse.ok(result)
    }
}
