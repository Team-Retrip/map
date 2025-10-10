package com.retrip.map.infra.adapter.`in`.presentation

import com.retrip.map.application.`in`.request.LocationCreateRequest
import com.retrip.map.application.`in`.request.LocationUpdateRequest
import com.retrip.map.application.`in`.response.LocationCreateResponse
import com.retrip.map.application.`in`.response.LocationResponse
import com.retrip.map.application.`in`.response.LocationUpdateResponse
import com.retrip.map.application.`in`.usecase.LocationUseCase
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
@RequestMapping("/location")
class LocationController(
    private val locationUseCase: LocationUseCase
) {

    @GetMapping("")
    @Schema(description = "장소 전체 조회")
    fun getLocation(
        @RequestParam(name = "locationId") id: UUID?,
        @PageableDefault(size = 10, page = 0) page: Pageable
    ): ApiResponse<Page<LocationResponse>> {
        val result = locationUseCase.getLocation(id, page)
        return ApiResponse.ok(result)
    }

    @PostMapping("")
    @Schema(description = "장소 등록")
    fun createLocation(
        @RequestBody request: LocationCreateRequest
    ): ApiResponse<LocationCreateResponse> {
        val result = locationUseCase.createLocation(request)
        return ApiResponse.create(result)
    }

    @PutMapping("/{locationId}")
    @Schema(description = "장소 수정")
    fun updateLocation(
        @PathVariable locationId: UUID,
        @RequestBody request: LocationUpdateRequest
    ): ApiResponse<LocationUpdateResponse> {
        val result = locationUseCase.updateLocation(locationId, request)
        return ApiResponse.ok(result)
    }

    @DeleteMapping("/{locationId}")
    @Schema(description = "장소 삭제")
    fun deleteLocation(
        @PathVariable locationId: UUID
    ): ApiResponse<Unit> {
        locationUseCase.deleteLocation(locationId)
        return ApiResponse.noContent()
    }


}
