package com.retrip.map.infra.adapter.`in`.presentation.rest

import com.retrip.map.application.`in`.request.context.UserContext
import com.retrip.map.application.`in`.request.context.WithUserContext
import com.retrip.map.application.`in`.response.LocationRecentSearchResponse
import com.retrip.map.application.`in`.response.LocationSearchResponse
import com.retrip.map.application.`in`.usecase.LocationRecentSearchUseCase
import com.retrip.map.application.`in`.usecase.LocationSearchUseCase
import com.retrip.map.infra.adapter.`in`.presentation.common.ApiResponse
import io.swagger.v3.oas.annotations.media.Schema
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
@RequestMapping("search/locations")
class LocationSearchController(
    private val locationSearchUseCase: LocationSearchUseCase,
    private val locationRecentSearchUseCase: LocationRecentSearchUseCase
) {

    @GetMapping("")
    @Schema(description = "장소 검색 엔진 조회")
    fun getLocation(
        @WithUserContext context: UserContext,
        @PageableDefault(size = 10, page = 0) page: Pageable,
        @RequestParam(name = "searchText", required = false) searchText: String?
    ): ApiResponse<Page<LocationSearchResponse>> {
        val result = locationSearchUseCase.getLocation(searchText, page, context)
        return ApiResponse.ok(result)
    }


    @GetMapping("recent")
    @Schema(description = "최근 장소 조회")
    fun getRecentLocation(
        @WithUserContext context: UserContext
    ): ApiResponse<LocationRecentSearchResponse?> {
        val result = locationRecentSearchUseCase.getRecentLocation(context)
        return ApiResponse.ok(result)
    }

    @DeleteMapping("recent")
    @Schema(description = "최근 장소 검색 제거")
    fun delectRecentLocationsByKeyword(
        @WithUserContext context: UserContext,
        @RequestParam("keyword", required = false) keyword: String?
    ): ApiResponse<LocationRecentSearchResponse?> {
        locationRecentSearchUseCase.delectRecentLocationsByKeyword(context, keyword)
        return ApiResponse.noContent()
    }

}
