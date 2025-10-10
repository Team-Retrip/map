package com.retrip.map.domain.exception

import com.retrip.map.domain.exception.common.ErrorCode
import jakarta.persistence.EntityNotFoundException

class LocationNotFoundException : EntityNotFoundException() {
    companion object {
        val errorCode = ErrorCode.LOCATION_NOT_FOUND
    }

}
