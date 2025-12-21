package com.retrip.map.infra.adapter.`in`.batch

import com.retrip.map.application.`in`.usecase.LocationIndexUseCase
import com.retrip.map.infra.adapter.out.search.elasticsearch.entity.LocationDetailDocument
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component

@Component
class MapWriter(
    val locationIndexUseCase: LocationIndexUseCase
) : ItemWriter<List<LocationDetailDocument>> {
    override fun write(chunk: Chunk<out List<LocationDetailDocument>?>) {
        chunk.items.forEach { documents -> locationIndexUseCase.indexLocationDetailDocuments(documents) }
    }
}
