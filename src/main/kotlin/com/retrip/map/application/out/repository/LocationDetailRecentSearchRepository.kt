package com.retrip.map.application.out.repository

import com.retrip.map.domain.entity.LocationDetailRecentSearch
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.util.*

interface LocationDetailRecentSearchRepository : JpaRepository<LocationDetailRecentSearch, UUID> {

    fun findByMemberIdAndKeyword(memberId: UUID, keyword: String): LocationDetailRecentSearch?
    fun findByMemberId(memberId: UUID, pageable: Pageable): List<LocationDetailRecentSearch>?
    fun findByMemberId(memberId: UUID): List<LocationDetailRecentSearch>?

    @Query(
        """
            SELECT r.lastSearchedAt
            FROM LocationDetailRecentSearch r
            WHERE r.memberId = :memberId
            ORDER BY r.lastSearchedAt DESC
    """
    )
    fun findThresholdTime(memberId: UUID, pageable: Pageable): List<LocalDateTime>?

    @Modifying
    @Query(
        """
       DELETE FROM LocationDetailRecentSearch r WHERE r.memberId = :memberId AND r.lastSearchedAt < :threshold
    """
    )
    fun deleteOlderThan(memberId: UUID, threshold: LocalDateTime)
}
