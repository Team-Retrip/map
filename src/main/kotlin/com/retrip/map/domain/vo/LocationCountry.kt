package com.retrip.map.domain.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable


@Embeddable
class LocationCountry() {
    @Column(name = "country")
    var value: String = ""

    constructor(value: String) : this() {
        validate(value)
        this.value = value
    }

    private fun validate(value: String) {
        require(value.isNotBlank()) { "국가 정보는 필수입니다." }
    }
}
