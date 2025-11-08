package com.retrip.map.application.`in`.usecase

import com.retrip.map.infra.adapter.out.search.elasticsearch.entity.LocationDocument

interface LocationIndexUseCase {
    fun indexLocationDocuments(documents: List<LocationDocument>?)
}
