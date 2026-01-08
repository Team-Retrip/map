package com.retrip.map.infra.adapter.`in`.presentation

import com.retrip.map.application.`in`.request.LocationCreateRequest
import com.retrip.map.application.`in`.request.LocationDetailCreateRequest
import com.retrip.map.application.`in`.request.LocationDetailUpdateRequest
import com.retrip.map.application.`in`.request.LocationUpdateRequest
import com.retrip.map.application.`in`.response.LocationCreateResponse
import com.retrip.map.application.`in`.response.LocationDetailCreateResponse
import com.retrip.map.application.`in`.response.LocationDetailResponse
import com.retrip.map.application.`in`.response.LocationDetailUpdateResponse
import com.retrip.map.application.`in`.response.LocationResponse
import com.retrip.map.application.`in`.response.LocationSearchResponse
import com.retrip.map.application.`in`.response.LocationUpdateResponse
import com.retrip.map.application.`in`.usecase.LocationDetailUseCase
import com.retrip.map.application.`in`.usecase.LocationSearchUseCase
import com.retrip.map.application.`in`.usecase.LocationUseCase
import com.retrip.map.infra.adapter.`in`.presentation.common.ApiResponse
import io.swagger.v3.oas.annotations.media.Schema
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.AbstractPersistable_.id
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
@RequestMapping("/location-search")
class LocationSearchController(
    private val locationSearchUseCase: LocationSearchUseCase
) {

    @GetMapping("")
    @Schema(description = "장소 검색 엔진 조회")
    fun getLocation(
        @PageableDefault(size = 10, page = 0) page: Pageable,
        @RequestParam(name = "name", required = false) name: String?
    ): ApiResponse<Page<LocationSearchResponse>> {
        val result = locationSearchUseCase.getLocation(name, page)
        return ApiResponse.ok(result)
    }
}
