package com.retrip.map.application.`in`.usecase

import com.retrip.map.infra.adapter.out.search.elasticsearch.entity.LocationDetailDocument

interface LocationIndexUseCase {
    fun indexLocationDetailDocuments(documents: List<LocationDetailDocument>?)
}
