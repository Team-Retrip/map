package com.retrip.map.domain.entity

import jakarta.persistence.Column
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
class LocationSearchHistory(
    @Id
    @Column(columnDefinition = "varbinary(16)")
    val id: UUID? = null,

    val searchText: String,

    val memberId: UUID,

    @Version
    private val version: Long? = null,
): BaseEntity() {
    companion object {
        fun create(searchText: String, memberId: UUID): LocationSearchHistory {
            return LocationSearchHistory(
                id = UUID.randomUUID(),
                searchText = searchText,
                memberId = memberId,
            )
        }

    }
}
