package com.retrip.map.application.`in`.service

import com.retrip.map.application.`in`.request.LocationDetailRecentSearchModel
import com.retrip.map.application.`in`.request.context.UserContext
import com.retrip.map.application.`in`.response.LocationDetailRecentSearchResponse
import com.retrip.map.application.`in`.usecase.LocationDetailRecentSearchUseCase
import com.retrip.map.application.out.repository.LocationDetailRecentSearchRepository
import com.retrip.map.domain.entity.LocationDetailRecentSearch
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class LocationDetailRecentSearchService(
    private val locationDetailRecentSearchRepository: LocationDetailRecentSearchRepository
) : LocationDetailRecentSearchUseCase {

    @Transactional(readOnly = true)
    override fun getRecentLocationDetail(context: UserContext): LocationDetailRecentSearchResponse {
        val memberId = context.memberId ?: return LocationDetailRecentSearchResponse()
        val pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "lastSearchedAt"))
        val result = locationDetailRecentSearchRepository.findByMemberId(memberId, pageRequest)
        return LocationDetailRecentSearchResponse(
            result?.map { it.keyword } ?: emptyList()
        )
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun addLocationDetailRecentSearch(model: LocationDetailRecentSearchModel) {
        val existing = locationDetailRecentSearchRepository.findByMemberIdAndKeyword(
            model.memberId,
            model.searchText,
        )
        if (existing != null) {
            existing.updateTime(model.updateTime)
        } else {
            locationDetailRecentSearchRepository.save(
                LocationDetailRecentSearch.create(
                    model.memberId,
                    model.searchText,
                    model.updateTime
                )
            )
        }
        val pageRequest = PageRequest.of(9, 1, Sort.by(Sort.Direction.DESC, "lastSearchedAt"))
        val threshold = locationDetailRecentSearchRepository.findThresholdTime(
            model.memberId,
            pageRequest
        )?.firstOrNull()

        threshold?.let {
            locationDetailRecentSearchRepository.deleteOlderThan(model.memberId, it)
        }
    }

    @Transactional
    override fun deleteRecentLocationDetailsByKeyword(context: UserContext, keyword: String?) {
        val memberId = context.memberId ?: return
        if (keyword != null) {
            val recentSearch = locationDetailRecentSearchRepository.findByMemberIdAndKeyword(memberId, keyword)
            recentSearch?.run {
                locationDetailRecentSearchRepository.delete(this)
            }
        } else {
            val recentSearches = locationDetailRecentSearchRepository.findByMemberId(memberId)
            recentSearches?.run {
                locationDetailRecentSearchRepository.deleteAllInBatch(this)
            }
        }
    }
}
