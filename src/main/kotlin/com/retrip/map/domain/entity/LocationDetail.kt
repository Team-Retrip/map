package com.retrip.map.domain.entity

import com.retrip.map.domain.vo.LocationDetailAddress
import com.retrip.map.domain.vo.LocationDetailCategory
import com.retrip.map.domain.vo.LocationDetailDescription
import com.retrip.map.domain.vo.LocationDetailGeoPoint
import com.retrip.map.domain.vo.LocationDetailName
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.Version
import lombok.AccessLevel
import lombok.NoArgsConstructor
import lombok.Setter
import java.util.*

@Entity
@Table(name = "location-details")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(value = AccessLevel.PROTECTED)
class LocationDetail(
    @Id
    @Column(columnDefinition = "varbinary(16)")
    val id: UUID? = null,

    @Embedded
    var name: LocationDetailName? = null,

    @Embedded
    var category: LocationDetailCategory? = null,

    @Embedded
    var description: LocationDetailDescription? = null,

    @Column(name = "telephone")
    var telephone: String? = null,

    @Embedded
    var address: LocationDetailAddress? = null,

    @Embedded
    var geoPoint: LocationDetailGeoPoint? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "location_id",
        nullable = false,
        columnDefinition = "varbinary(16)",
        foreignKey = ForeignKey(name = "fk_location_details_to_location")
    )
    var location: Location? = null,

    @Version
    private val version: Long? = null,
): BaseEntity() {
    fun update(
        location: Location,
        name: String,
        category: String,
        description: String?,
        telephone: String?,
        address: String?,
        roadAddress: String?,
        latitude: Double,
        longitude: Double
    ) {
        this.location = location
        this.name = LocationDetailName(name)
        this.category = LocationDetailCategory(category)
        this.description = LocationDetailDescription(description)
        this.telephone = telephone
        this.address = LocationDetailAddress(address, roadAddress)
        this.geoPoint = LocationDetailGeoPoint(latitude, longitude)
    }

    companion object {
        fun create(
            location: Location,
            name: String,
            category: String,
            description: String?,
            telephone: String?,
            address: String?,
            roadAddress: String?,
            latitude: Double,
            longitude: Double
        ): LocationDetail {
            return LocationDetail(
                id = UUID.randomUUID(),
                location = location,
                name = LocationDetailName(name),
                category = LocationDetailCategory(category),
                description = LocationDetailDescription(description),
                telephone = telephone,
                address = LocationDetailAddress(address, roadAddress),
                geoPoint = LocationDetailGeoPoint(latitude, longitude)
            )
        }
    }
}
