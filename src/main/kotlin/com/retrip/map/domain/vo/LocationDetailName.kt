package com.retrip.map.domain.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable


@Embeddable
class LocationDetailName() {
    @Column(name = "name")
    var value: String = ""

    constructor(value: String) : this() {
        validate(value)
        this.value = value
    }

    private fun validate(value: String) {
        require(value.isNotBlank()) { "장소명은 필수입니다." }
    }
}
