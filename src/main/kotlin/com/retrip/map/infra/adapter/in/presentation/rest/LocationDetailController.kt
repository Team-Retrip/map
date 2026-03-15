package com.retrip.map.infra.adapter.`in`.presentation.rest

import com.retrip.map.application.`in`.request.LocationDetailCreateRequest
import com.retrip.map.application.`in`.request.LocationDetailUpdateRequest
import com.retrip.map.application.`in`.response.LocationDetailCreateResponse
import com.retrip.map.application.`in`.response.LocationDetailResponse
import com.retrip.map.application.`in`.response.LocationDetailUpdateResponse
import com.retrip.map.application.`in`.usecase.LocationDetailUseCase
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
@Tag(name = "LocationDetailCRUD", description = "여행 상세 지역 정보 등록,수정,삭제,조회 API 입니다.")
@RequestMapping("/location-details")
class LocationDetailController(
    private val locationDetailUseCase: LocationDetailUseCase
) {

    @GetMapping("/{locationId}")
    @Operation(description = "장소 상세 전체 조회", summary = "장소 상세 전체 조회 API 입니다.")
    fun getLocationDetail(
        @PathVariable locationId: UUID,
        @RequestParam(name = "locationDetailId") id: UUID?,
        @PageableDefault(size = 10, page = 0) page: Pageable
    ): ApiResponse<Page<LocationDetailResponse>> {
        val result = locationDetailUseCase.getLocationDetail(locationId, id, page)
        return ApiResponse.ok(result)
    }

    @PostMapping("/{locationId}")
    @Operation(description = "장소 상세 등록", summary = "장소 상세 전체 등록 API 입니다.")
    fun createLocationDetail(
        @PathVariable locationId: UUID,
        @RequestBody request: LocationDetailCreateRequest
    ): ApiResponse<LocationDetailCreateResponse> {
        val result = locationDetailUseCase.createLocationDetail(locationId, request)
        return ApiResponse.create(result)
    }

    @PutMapping("/{locationId}/{locationDetailId}")
    @Operation(description = "장소 상세 수정", summary = "장소 상세 전체 수정 API 입니다.")
    fun updateLocationDetail(
        @PathVariable locationId: UUID,
        @PathVariable locationDetailId: UUID,
        @RequestBody request: LocationDetailUpdateRequest
    ): ApiResponse<LocationDetailUpdateResponse> {
        val result = locationDetailUseCase.updateLocationDetail(locationId, locationDetailId, request)
        return ApiResponse.ok(result)
    }

    @DeleteMapping("/{locationDetailId}")
    @Operation(description = "장소 상세 삭제", summary = "장소 상세 전체 삭제 API 입니다.")
    fun deleteLocationDetail(
        @PathVariable locationDetailId: UUID
    ): ApiResponse<Unit> {
        locationDetailUseCase.deleteLocationDetail(locationDetailId)
        return ApiResponse.noContent()
    }


}
