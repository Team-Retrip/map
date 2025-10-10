package com.retrip.map.infra.adapter.`in`.batch

import com.retrip.map.domain.entity.Location
import org.springframework.batch.item.ItemStreamReader
import org.springframework.stereotype.Component

@Component
class DestinationReader(
): ItemStreamReader<Location> {
    override fun read(): Location? {
        return null
    }

}
