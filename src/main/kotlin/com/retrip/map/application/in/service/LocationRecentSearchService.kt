package com.retrip.map.application.`in`.service

import com.retrip.map.application.`in`.request.LocationRecentSearchModel
import com.retrip.map.application.`in`.request.context.UserContext
import com.retrip.map.application.`in`.response.LocationRecentSearchResponse
import com.retrip.map.application.`in`.usecase.LocationRecentSearchUseCase
import com.retrip.map.application.out.repository.LocationRecentSearchRepository
import com.retrip.map.domain.entity.LocationRecentSearch
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
@Transactional
class LocationRecentSearchService(
    private val locationRecentSearchRepository: LocationRecentSearchRepository
) : LocationRecentSearchUseCase {

    override fun getRecentLocation(context: UserContext): LocationRecentSearchResponse? {
        val pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "lastSearchedAt"))
        //최대 10개만 조회
        val result = locationRecentSearchRepository.findByMemberId(context.memberId, pageRequest)
        return result?.let {
            LocationRecentSearchResponse(
                it.map { recentSearch -> recentSearch.keyword }
            )
        }

    }

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

    override fun delectRecentLocationsByKeyword(context: UserContext, keyword: String?) {
        if (keyword != null) {
            //단건 제거
            val recentSearchKeyword =
                locationRecentSearchRepository.findByMemberIdAndKeyword(context.memberId, keyword)
            recentSearchKeyword?.run {
                locationRecentSearchRepository.delete(this)
            }
        } else {
            //모두 제거
            val recentSearchKeywords = locationRecentSearchRepository.findByMemberId(context.memberId)
            recentSearchKeywords?.run {
                locationRecentSearchRepository.deleteAllInBatch(this)
            }
        }
    }
}

