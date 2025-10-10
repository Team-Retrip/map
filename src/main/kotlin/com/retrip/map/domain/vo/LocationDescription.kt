package com.retrip.map.domain.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable


@Embeddable
class LocationDescription() {
    @Column(name = "description")
    var value: String? = null

    constructor(value: String?) : this() {
        validate(value)
        this.value = value
    }

    private fun validate(value: String?) {
        if (!value.isNullOrEmpty() && value.length >= LENGTH_LIMIT) {
            throw IllegalStateException("장소 설명은 $LENGTH_LIMIT 자를 넘을 수 없습니다.")
        }
    }

    companion object {
        private const val LENGTH_LIMIT = 50
    }
}
