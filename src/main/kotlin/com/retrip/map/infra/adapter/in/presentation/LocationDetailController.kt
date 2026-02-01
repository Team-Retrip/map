package com.retrip.map.infra.adapter.`in`.presentation

import com.retrip.map.application.`in`.request.LocationDetailCreateRequest
import com.retrip.map.application.`in`.request.LocationDetailUpdateRequest
import com.retrip.map.application.`in`.response.LocationDetailCreateResponse
import com.retrip.map.application.`in`.response.LocationDetailResponse
import com.retrip.map.application.`in`.response.LocationDetailUpdateResponse
import com.retrip.map.application.`in`.usecase.LocationDetailUseCase
import com.retrip.map.infra.adapter.`in`.presentation.common.ApiResponse
import io.swagger.v3.oas.annotations.media.Schema
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
@RequestMapping("/location-detail")
class LocationDetailController(
    private val locationDetailUseCase: LocationDetailUseCase
) {

    @GetMapping("/{locationId}")
    @Schema(description = "장소 상세 전체 조회")
    fun getLocationDetail(
        @PathVariable locationId: UUID,
        @RequestParam(name = "locationDetailId") id: UUID?,
        @PageableDefault(size = 10, page = 0) page: Pageable
    ): ApiResponse<Page<LocationDetailResponse>> {
        val result = locationDetailUseCase.getLocationDetail(locationId, id, page)
        return ApiResponse.ok(result)
    }

    @PostMapping("/{locationId}")
    @Schema(description = "장소 상세 등록")
    fun createLocationDetail(
        @PathVariable locationId: UUID,
        @RequestBody request: LocationDetailCreateRequest
    ): ApiResponse<LocationDetailCreateResponse> {
        val result = locationDetailUseCase.createLocationDetail(locationId, request)
        return ApiResponse.create(result)
    }

    @PutMapping("/{locationId}/{locationDetailId}")
    @Schema(description = "장소 상세 수정")
    fun updateLocationDetail(
        @PathVariable locationId: UUID,
        @PathVariable locationDetailId: UUID,
        @RequestBody request: LocationDetailUpdateRequest
    ): ApiResponse<LocationDetailUpdateResponse> {
        val result = locationDetailUseCase.updateLocationDetail(locationId, locationDetailId, request)
        return ApiResponse.ok(result)
    }

    @DeleteMapping("/{locationDetailId}")
    @Schema(description = "장소 상세 삭제")
    fun deleteLocationDetail(
        @PathVariable locationDetailId: UUID
    ): ApiResponse<Unit> {
        locationDetailUseCase.deleteLocationDetail(locationDetailId)
        return ApiResponse.noContent()
    }


}
