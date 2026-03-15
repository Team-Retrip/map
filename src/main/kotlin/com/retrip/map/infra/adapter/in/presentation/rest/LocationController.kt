package com.retrip.map.infra.adapter.`in`.presentation.rest

import com.retrip.map.application.`in`.request.LocationCreateRequest
import com.retrip.map.application.`in`.request.LocationUpdateRequest
import com.retrip.map.application.`in`.response.LocationCreateResponse
import com.retrip.map.application.`in`.response.LocationResponse
import com.retrip.map.application.`in`.response.LocationUpdateResponse
import com.retrip.map.application.`in`.usecase.LocationUseCase
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
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequiredArgsConstructor
@Tag(name = "LocationCRUD", description = "여행 지역 정보 등록,수정,삭제,조회 API 입니다.")
@RequestMapping("/locations")
class LocationController(
    private val locationUseCase: LocationUseCase
) {

    @GetMapping("")
    @Operation( summary = "여행 지역 조회",description = "여행 지역 조회 API 입니다.")
    fun getLocation(
        @RequestParam(name = "locationId") id: UUID?,
        @PageableDefault(size = 10, page = 0) page: Pageable
    ): ApiResponse<Page<LocationResponse>> {
        val result = locationUseCase.getLocation(id, page)
        return ApiResponse.ok(result)
    }

    @PostMapping("")
    @Operation( summary = "여행 지역 등록",description = "여행 지역 등록 API 입니다.")
    fun createLocation(
        @RequestBody request: LocationCreateRequest
    ): ApiResponse<LocationCreateResponse> {
        val result = locationUseCase.createLocation(request)
        return ApiResponse.create(result)
    }

    @PutMapping("/{locationId}")
    @Operation( summary = "여행 지역 수정",description = "여행 지역 수정 API 입니다.")
    fun updateLocation(
        @PathVariable locationId: UUID,
        @RequestBody request: LocationUpdateRequest
    ): ApiResponse<LocationUpdateResponse> {
        val result = locationUseCase.updateLocation(locationId, request)
        return ApiResponse.ok(result)
    }

    @DeleteMapping("/{locationId}")
    @Operation( summary = "여행 지역 삭제",description = "여행 지역 삭제 API 입니다.")
    fun deleteLocation(
        @PathVariable locationId: UUID
    ): ApiResponse<Unit> {
        locationUseCase.deleteLocation(locationId)
        return ApiResponse.noContent()
    }
}
