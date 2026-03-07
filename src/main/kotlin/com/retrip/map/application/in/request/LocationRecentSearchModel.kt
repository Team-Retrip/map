package com.retrip.map.application.`in`.request

import java.time.LocalDateTime
import java.util.UUID

data class LocationRecentSearchModel(
    val memberId: UUID,
    val searchText: String,
    val updateTime: LocalDateTime = LocalDateTime.now(),
) {
}
