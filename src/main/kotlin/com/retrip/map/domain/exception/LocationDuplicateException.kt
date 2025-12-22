package com.retrip.map.domain.exception

import com.retrip.map.domain.exception.common.BusinessException
import com.retrip.map.domain.exception.common.ErrorCode

class LocationDuplicateException : BusinessException {
    constructor(): super(ErrorCode.LOCATION_DUPLICATION) {}
    constructor(errorCode: ErrorCode): super(errorCode) {}
    constructor(message: String): super(ErrorCode.LOCATION_DUPLICATION, message) {}
    constructor(errorCode: ErrorCode, message: String): super(errorCode, message) {}

}
