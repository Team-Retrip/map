package com.retrip.map.domain.entity

import com.retrip.map.domain.vo.LocationAddress
import com.retrip.map.domain.vo.LocationCategory
import com.retrip.map.domain.vo.LocationDescription
import com.retrip.map.domain.vo.LocationGeoPoint
import com.retrip.map.domain.vo.LocationName
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Version
import lombok.AccessLevel
import lombok.NoArgsConstructor
import lombok.Setter
import java.util.*

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(value = AccessLevel.PROTECTED)
class Location(
    @Id
    @Column(columnDefinition = "varbinary(16)")
    val id: UUID? = null,

    @Embedded
    var name: LocationName? = null,

    @Embedded
    var category: LocationCategory? = null,

    @Embedded
    var description: LocationDescription? = null,

    @Column(name = "telephone")
    var telephone: String? = null,

    @Embedded
    var address: LocationAddress? = null,

    @Embedded
    var geoPoint: LocationGeoPoint? = null,

    @Version
    private val version: Long? = null,
): BaseEntity() {
    fun update(
        name: String,
        category: String,
        description: String?,
        telephone: String?,
        address: String?,
        roadAddress: String?,
        latitude: Double,
        longitude: Double
    ) {
        this.name = LocationName(name)
        this.category = LocationCategory(category)
        this.description = LocationDescription(description)
        this.telephone = telephone
        this.address = LocationAddress(address, roadAddress)
        this.geoPoint = LocationGeoPoint(latitude, longitude)
    }

    companion object {
        fun create(
            name: String,
            category: String,
            description: String?,
            telephone: String?,
            address: String?,
            roadAddress: String?,
            latitude: Double,
            longitude: Double
        ): Location {
            return Location(
                id = UUID.randomUUID(),
                name = LocationName(name),
                category = LocationCategory(category),
                description = LocationDescription(description),
                telephone = telephone,
                address = LocationAddress(address, roadAddress),
                geoPoint = LocationGeoPoint(latitude, longitude)
            )
        }
    }
}
