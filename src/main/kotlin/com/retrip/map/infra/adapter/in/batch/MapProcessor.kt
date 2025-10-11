package com.retrip.map.infra.adapter.`in`.batch

import com.retrip.map.domain.entity.Location
import com.retrip.map.infra.adapter.out.search.elasticsearch.entity.LocationDocument
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class MapProcessor()
    : ItemProcessor<List<Location>, List<LocationDocument>> {
    override fun process(item: List<Location>): List<LocationDocument>? {
        return item.map { LocationDocument.of(it) }

    }
}
