package com.retrip.map.domain.exception.common

open class BusinessException : RuntimeException {
    open var errorCode: ErrorCode = ErrorCode.SERVER_ERROR

    constructor() : super(ErrorCode.SERVER_ERROR.message) {
        this.errorCode = ErrorCode.SERVER_ERROR
    }

    constructor(errorCode: ErrorCode) : super(errorCode.message) {
        this.errorCode = errorCode
    }

    constructor(errorCode: ErrorCode, message: String?) : super(message) {
        this.errorCode = errorCode
    }
}
