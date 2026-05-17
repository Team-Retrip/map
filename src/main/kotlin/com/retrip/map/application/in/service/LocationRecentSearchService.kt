package com.retrip.map.application.`in`.service

import com.retrip.map.application.`in`.request.LocationRecentSearchModel
import com.retrip.map.application.`in`.request.context.UserContext
import com.retrip.map.application.`in`.response.LocationRecentSearchResponse
import com.retrip.map.application.`in`.usecase.LocationRecentSearchUseCase
import com.retrip.map.application.out.repository.LocationRecentSearchRepository
import com.retrip.map.domain.entity.LocationRecentSearch
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LocationRecentSearchService(
    private val locationRecentSearchRepository: LocationRecentSearchRepository
) : LocationRecentSearchUseCase {

    @Transactional(readOnly = true)
    override fun getRecentLocation(context: UserContext): LocationRecentSearchResponse {
        val pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "lastSearchedAt"))
        val result = locationRecentSearchRepository.findByMemberId(context.memberId, pageRequest)
        return LocationRecentSearchResponse(
            result?.map { it.keyword } ?: emptyList()
        )
    }

    @Transactional
    override fun addLocationRecentSearch(locationRecentSearchModel: LocationRecentSearchModel) {
        val existingLocationRecentSearch = locationRecentSearchRepository.findByMemberIdAndKeyword(
            locationRecentSearchModel.memberId,
            locationRecentSearchModel.searchText,
        )
        if (existingLocationRecentSearch != null) {
            existingLocationRecentSearch.updateTime(
                locationRecentSearchModel.updateTime
            )
        } else {
            locationRecentSearchRepository.save(
                LocationRecentSearch.create(
                    locationRecentSearchModel.memberId,
                    locationRecentSearchModel.searchText,
                    locationRecentSearchModel.updateTime
                )
            )
        }
        val pageRequest = PageRequest.of(9, 1, Sort.by(Sort.Direction.DESC, "lastSearchedAt"))
        val threshold = locationRecentSearchRepository.findThresholdTime(
            locationRecentSearchModel.memberId,
            pageRequest
        )?.firstOrNull()

        threshold?.let {
            locationRecentSearchRepository.deleteOlderThan(
                locationRecentSearchModel.memberId, it
            )
        }
    }

    @Transactional
    override fun deleteRecentLocationsByKeyword(context: UserContext, keyword: String?) {
        if (keyword != null) {
            val recentSearchKeyword =
                locationRecentSearchRepository.findByMemberIdAndKeyword(context.memberId, keyword)
            recentSearchKeyword?.run {
                locationRecentSearchRepository.delete(this)
            }
        } else {
            val recentSearchKeywords = locationRecentSearchRepository.findByMemberId(context.memberId)
            recentSearchKeywords?.run {
                locationRecentSearchRepository.deleteAllInBatch(this)
            }
        }
    }
}
