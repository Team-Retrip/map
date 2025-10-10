package com.retrip.map.infra.adapter.`in`.presentation.common

import com.retrip.map.domain.exception.common.ErrorCode
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError

data class ErrorResponse(
    val status: Int,
    val code: String,
    val message: String,
    val url: String,
    val method: String,
    val errors: List<FieldError> = listOf()
) {
    companion object {
        fun of(
            code: ErrorCode,
            url: String,
            method: String,
            bindingResult: BindingResult
        ): ErrorResponse {
            return ErrorResponse(
                code.status.value(),
                code.code,
                code.message,
                url,
                method,
                bindingResult.fieldErrors
            )
        }

        fun of(
            code: ErrorCode,
            url: String,
            method: String
        ): ErrorResponse {
            return ErrorResponse(
                code.status.value(),
                code.code,
                code.message,
                url,
                method
            )
        }
    }
}
