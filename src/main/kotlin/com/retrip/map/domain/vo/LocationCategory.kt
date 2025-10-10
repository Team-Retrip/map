package com.retrip.map.domain.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable


@Embeddable
class LocationCategory() {
    @Column(name = "category")
    var value: String = ""


    constructor(value: String) : this() {
        validate(value)
        this.value = value
    }

    private fun validate(value: String) {
        require(value.isNotBlank()) { "카테고리는 필수입니다." }
    }
}
