package com.retrip.map.domain.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable


@Embeddable
class LocationDetailGeoPoint() {
    @Column(name = "latitude")
    var latitude: Double = 0.0

    @Column(name = "longitude")
    var longitude: Double = 0.0

    constructor(latitude: Double, longitude: Double) : this() {
        validate(latitude, longitude)
        this.latitude = latitude
        this.longitude = longitude
    }

    private fun validate(latitude: Double, longitude: Double) {
        if (latitude == 0.0 && longitude == 0.0) {
            throw IllegalArgumentException("장소 위도, 경도가 모두 0일 수 없습니다.")
        }
    }

}
