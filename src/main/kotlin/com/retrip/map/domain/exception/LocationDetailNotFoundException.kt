package com.retrip.map.domain.exception

import com.retrip.map.domain.exception.common.BusinessException
import com.retrip.map.domain.exception.common.ErrorCode

class LocationDetailNotFoundException : BusinessException {
    constructor(): super(ErrorCode.LOCATION_DETAIL_NOT_FOUND) {}
    constructor(errorCode: ErrorCode): super(errorCode) {}
    constructor(message: String): super(ErrorCode.LOCATION_DETAIL_NOT_FOUND, message) {}
    constructor(errorCode: ErrorCode, message: String): super(errorCode, message) {}

}
