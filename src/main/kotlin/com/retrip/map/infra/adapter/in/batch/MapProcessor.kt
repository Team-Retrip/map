package com.retrip.map.infra.adapter.`in`.batch

import com.retrip.map.domain.entity.LocationDetail
import com.retrip.map.infra.adapter.out.search.elasticsearch.entity.LocationDetailDocument
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class MapProcessor()
    : ItemProcessor<List<LocationDetail>, List<LocationDetailDocument>> {
    override fun process(item: List<LocationDetail>): List<LocationDetailDocument>? {
        return item.map { LocationDetailDocument.of(it) }
    }
}
