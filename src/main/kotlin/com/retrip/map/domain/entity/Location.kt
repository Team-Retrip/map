package com.retrip.map.domain.entity

import com.retrip.map.domain.vo.LocationCountry
import com.retrip.map.domain.vo.LocationDetailGeoPoint
import com.retrip.map.domain.vo.LocationDetailName
import com.retrip.map.domain.vo.LocationGeoPoint
import com.retrip.map.domain.vo.LocationName
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Version
import lombok.AccessLevel
import lombok.NoArgsConstructor
import lombok.Setter
import java.util.*

@Entity
@Table(name = "location")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(value = AccessLevel.PROTECTED)
class Location(
    @Id
    @Column(columnDefinition = "varbinary(16)")
    val id: UUID? = null,

    @Embedded
    var name: LocationName? = null,

    @Embedded
    var country: LocationCountry? = null,

    @Embedded
    var geoPoint: LocationGeoPoint? = null,

    @Version
    private val version: Long? = null,
): BaseEntity() {
    fun update(
        name: String,
        country: String,
        latitude: Double,
        longitude: Double
    ) {
        this.name = LocationName(name)
        this.country = LocationCountry(country)
        this.geoPoint = LocationGeoPoint(latitude, longitude)
    }

    companion object {
        fun create(
            name: String,
            country: String,
            latitude: Double,
            longitude: Double
        ): Location {
            return Location(
                id = UUID.randomUUID(),
                name = LocationName(name),
                country = LocationCountry(country),
                geoPoint = LocationGeoPoint(latitude, longitude)
            )
        }
    }
}
