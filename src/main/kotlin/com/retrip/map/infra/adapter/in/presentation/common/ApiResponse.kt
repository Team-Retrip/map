package com.retrip.map.infra.adapter.`in`.presentation.common

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.http.HttpStatus.OK


data class ApiResponse<T>(
    val success: Boolean,
    val status: Int,
    val message: String?,
    val data: T?
) {
    companion object {
        fun <T> create(data: T): ApiResponse<T> {
            return success(data, CREATED)
        }

        fun <T> ok(data: T): ApiResponse<T> {
            return success(data, OK)
        }

        fun <T> noContent(): ApiResponse<T> {
            return success(null, NO_CONTENT)
        }

        fun <T> of(data: T, status: HttpStatus): ApiResponse<T> {
            return success(data, status)
        }

        private fun <T> success(data: T?, status: HttpStatus): ApiResponse<T> {
            return ApiResponse(
                true,
                status.value(),
                status.reasonPhrase,
                data
            )
        }

        fun of(errorResponse: ErrorResponse): ApiResponse<ErrorResponse> {
            return ApiResponse(
                false,
                errorResponse.status,
                HttpStatus.valueOf(errorResponse.status).reasonPhrase,
                errorResponse
            )
        }

    }
}
