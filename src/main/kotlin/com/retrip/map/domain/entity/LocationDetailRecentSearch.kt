package com.retrip.map.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import jakarta.persistence.Version
import lombok.AccessLevel
import lombok.NoArgsConstructor
import lombok.Setter
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(
    name = "location_detail_recent_search",
    uniqueConstraints = [UniqueConstraint(
        name = "uk_location_detail_user_keyword",
        columnNames = ["keyword", "memberId"]
    )],
    indexes = [
        Index(name = "idx_user_detail_recent_searched", columnList = "member_id, last_searched_at DESC")
    ]
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(value = AccessLevel.PROTECTED)
class LocationDetailRecentSearch(
    @Id
    @Column(columnDefinition = "varbinary(16)")
    val id: UUID? = null,

    @Column(nullable = false)
    val keyword: String,

    @Column(nullable = false)
    val memberId: UUID,

    @Column(nullable = false)
    var lastSearchedAt: LocalDateTime,

    @Version
    private val version: Long? = null,
) : BaseEntity() {
    fun updateTime(updateTime: LocalDateTime) {
        this.lastSearchedAt = updateTime
    }

    companion object {
        fun create(memberId: UUID, searchText: String, updateTime: LocalDateTime): LocationDetailRecentSearch {
            return LocationDetailRecentSearch(
                id = UUID.randomUUID(),
                keyword = searchText,
                memberId = memberId,
                lastSearchedAt = updateTime
            )
        }
    }
}
