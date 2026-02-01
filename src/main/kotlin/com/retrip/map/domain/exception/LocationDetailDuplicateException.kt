package com.retrip.map.domain.exception

import com.retrip.map.domain.exception.common.BusinessException
import com.retrip.map.domain.exception.common.ErrorCode

class LocationDetailDuplicateException : BusinessException {
    constructor(): super(ErrorCode.LOCATION_DETAILS_DUPLICATION) {}
    constructor(errorCode: ErrorCode): super(errorCode) {}
    constructor(message: String): super(ErrorCode.LOCATION_DETAILS_DUPLICATION, message) {}
    constructor(errorCode: ErrorCode, message: String): super(errorCode, message) {}

}
