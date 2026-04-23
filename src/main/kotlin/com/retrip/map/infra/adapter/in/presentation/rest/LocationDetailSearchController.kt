package com.retrip.map.infra.adapter.`in`.presentation.rest

import com.retrip.map.application.`in`.request.context.UserContext
import com.retrip.map.application.`in`.request.context.WithUserContext
import com.retrip.map.application.`in`.response.LocationDetailRecentSearchResponse
import com.retrip.map.application.`in`.response.LocationDetailSearchResponse
import com.retrip.map.application.`in`.usecase.LocationDetailRecentSearchUseCase
import com.retrip.map.application.`in`.usecase.LocationDetailSearchUseCase
import com.retrip.map.infra.adapter.`in`.presentation.common.ApiResponse
import com.retrip.map.infra.adapter.`in`.presentation.common.PageUtils
import io.swagger.v3.oas.annotations.Operation
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
import java.util.*

@RestController
@RequiredArgsConstructor
@RequestMapping("search/location-details")
@Tag(name = "LocationDetail", description = "여행 상세 지역 정보 조회용 API 입니다.")
class LocationDetailSearchController(
    private val locationDetailSearchUseCase: LocationDetailSearchUseCase,
    private val locationDetailRecentSearchUseCase: LocationDetailRecentSearchUseCase,
) {

    @Operation(summary = "여행 상세 지역 조회", description = "여행 상세 지역 조회(검색엔진)시, 사용하는 API 입니다.")
    @GetMapping("")
    fun getLocation(
        @WithUserContext context: UserContext,
        @PageableDefault(size = 10, page = 0) page: Pageable,
        @RequestParam(name = "locationId", required = false) locationId: UUID?,
        @RequestParam(name = "searchText", required = false) searchText: String?
    ): ApiResponse<Page<LocationDetailSearchResponse>> {
        val safePageable = PageUtils.getSafePageable(page)
        val result = locationDetailSearchUseCase.getDetailLocation(locationId, searchText, safePageable, context)
        return ApiResponse.ok(result)
    }

    @GetMapping("recent")
    @Operation(summary = "최근 여행 상세 지역 조회", description = "최근 여행 상세 지역 조회 API 입니다.")
    fun getRecentLocationDetail(
        @WithUserContext context: UserContext
    ): ApiResponse<LocationDetailRecentSearchResponse?> {
        val result = locationDetailRecentSearchUseCase.getRecentLocationDetail(context)
        return ApiResponse.ok(result)
    }

    @DeleteMapping("recent")
    @Operation(summary = "최근 여행 상세 지역 조회 제거", description = "최근 여행 상세 지역 조회 제거 API 입니다.")
    fun deleteRecentLocationDetailsByKeyword(
        @WithUserContext context: UserContext,
        @RequestParam("keyword", required = false) keyword: String?
    ): ApiResponse<LocationDetailRecentSearchResponse?> {
        locationDetailRecentSearchUseCase.deleteRecentLocationDetailsByKeyword(context, keyword)
        return ApiResponse.noContent()
    }
}
