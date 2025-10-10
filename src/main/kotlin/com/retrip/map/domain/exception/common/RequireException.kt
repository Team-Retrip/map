package com.retrip.map.domain.exception.common

class RequireException: BusinessException {

    constructor(): super(ErrorCode.ILLEGAL_STATE) {}
    constructor(errorCode: ErrorCode): super(errorCode) {}
    constructor(message: String): super(ErrorCode.ILLEGAL_STATE, message) {}
    constructor(errorCode: ErrorCode, message: String): super(errorCode, message) {}

}
