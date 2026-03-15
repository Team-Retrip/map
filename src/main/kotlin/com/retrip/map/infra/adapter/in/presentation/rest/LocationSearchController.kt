package com.retrip.map.infra.adapter.`in`.presentation.rest

import com.retrip.map.application.`in`.request.context.UserContext
import com.retrip.map.application.`in`.request.context.WithUserContext
import com.retrip.map.application.`in`.response.LocationRecentSearchResponse
import com.retrip.map.application.`in`.response.LocationSearchResponse
import com.retrip.map.application.`in`.usecase.LocationRecentSearchUseCase
import com.retrip.map.application.`in`.usecase.LocationSearchUseCase
import com.retrip.map.infra.adapter.`in`.presentation.common.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
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
@Tag(name = "Location", description = "여행 지역 정보 조회용 API 입니다.")
@RequestMapping("search/locations")
class LocationSearchController(
    private val locationSearchUseCase: LocationSearchUseCase,
    private val locationRecentSearchUseCase: LocationRecentSearchUseCase
) {

    @GetMapping("")
    @Operation(summary = "여행 지역 조회", description = "여행 지역 조회(검색엔진)시, 사용하는 API 입니다.")
    fun getLocation(
        @WithUserContext context: UserContext,
        @PageableDefault(size = 10, page = 0) page: Pageable,
        @RequestParam(name = "searchText", required = false) searchText: String?
    ): ApiResponse<Page<LocationSearchResponse>> {
        val result = locationSearchUseCase.getLocation(searchText, page, context)
        return ApiResponse.ok(result)
    }


    @GetMapping("recent")
    @Operation(summary = "최근 여행 지역 조회", description = "최근 여행 지역 조회 API 입니다.")
    fun getRecentLocation(
        @WithUserContext context: UserContext
    ): ApiResponse<LocationRecentSearchResponse?> {
        val result = locationRecentSearchUseCase.getRecentLocation(context)
        return ApiResponse.ok(result)
    }

    @DeleteMapping("recent")
    @Operation(summary = "최근 여행 지역 조회 제거", description = "최근 여행 지역 조회 제거 API 입니다.")
    fun delectRecentLocationsByKeyword(
        @WithUserContext context: UserContext,
        @RequestParam("keyword", required = false) keyword: String?
    ): ApiResponse<LocationRecentSearchResponse?> {
        locationRecentSearchUseCase.delectRecentLocationsByKeyword(context, keyword)
        return ApiResponse.noContent()
    }

}
