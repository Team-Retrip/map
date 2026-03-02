package com.retrip.map.application.out.repository

import com.retrip.map.domain.entity.LocationRecentSearch
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.util.*

interface LocationRecentSearchRepository : JpaRepository<LocationRecentSearch, UUID> {

    fun findByMemberIdAndKeyword(memberId: UUID, keyword: String): LocationRecentSearch?
    fun findByMemberId(memberId: UUID, pageable: Pageable): List<LocationRecentSearch>?
    fun findByMemberId(memberId: UUID): List<LocationRecentSearch>?

    @Query(
        """
            SELECT r.lastSearchedAt 
            FROM LocationRecentSearch r 
            WHERE r.memberId = :memberId 
            ORDER BY r.lastSearchedAt DESC
    """
    )
    fun findThresholdTime(memberId: UUID, pageable: Pageable): List<LocalDateTime>?

    @Modifying
    @Query(
        """
       DELETE FROM LocationRecentSearch r WHERE r.memberId = :memberId AND r.lastSearchedAt < :threshold 
    """
    )
    fun deleteOlderThan(memberId: UUID, it: LocalDateTime)

}
