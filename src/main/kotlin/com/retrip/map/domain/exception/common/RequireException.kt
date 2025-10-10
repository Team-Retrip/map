package com.retrip.map.domain.exception.common

class RequireException: BusinessException {

    constructor(): super(ErrorCode.ENTITY_NOT_FOUND) {}
    constructor(errorCode: ErrorCode): super(errorCode) {}
    constructor(message: String): super(ErrorCode.ENTITY_NOT_FOUND, message) {}
    constructor(errorCode: ErrorCode, message: String): super(errorCode, message) {}

}
