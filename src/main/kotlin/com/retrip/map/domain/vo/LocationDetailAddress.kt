package com.retrip.map.domain.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable


@Embeddable
class LocationDetailAddress() {
    @Column(name = "address")
    var address: String? = null

    @Column(name = "road_address")
    var roadAddress: String? = null

    constructor(address: String?, roadAddress: String?) : this() {
        validate(address, roadAddress)
        this.roadAddress = roadAddress
        this.address = address
    }

    private fun validate(address: String?, roadAddress: String?) {
        if (address == null && roadAddress == null) {
            throw IllegalStateException("장소 주소와 도로명 주소 둘 다 입력하지 않았습니다.")
        }
        if (!address.isNullOrEmpty() && address.length >= ADDRESS_LENGTH_LIMIT) {
            throw IllegalStateException("장소 주소는 $ADDRESS_LENGTH_LIMIT 자를 넘을 수 없습니다.")
        }
        if (!roadAddress.isNullOrEmpty() && roadAddress.length >= ROAD_ADDRESS_LENGTH_LIMIT) {
            throw IllegalStateException("장소 도로명 주소는 $ADDRESS_LENGTH_LIMIT 자를 넘을 수 없습니다.")
        }
    }

    companion object {
        private const val ADDRESS_LENGTH_LIMIT = 50
        private const val ROAD_ADDRESS_LENGTH_LIMIT = 50
    }
}
