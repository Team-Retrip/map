package com.retrip.map.application.`in`.usecase

import com.retrip.map.infra.adapter.out.search.elasticsearch.entity.LocationDetailDocument

interface LocationDetailIndexUseCase {
    fun indexLocationDetailDocuments(documents: List<LocationDetailDocument>?)
}
