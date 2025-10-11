package com.retrip.map.infra.adapter.`in`.batch

import com.retrip.map.application.`in`.usecase.LocationIndexUseCase
import com.retrip.map.infra.adapter.out.search.elasticsearch.entity.LocationDocument
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component

@Component
class MapWriter(
    val locationIndexUseCase: LocationIndexUseCase
) : ItemWriter<List<LocationDocument>> {
    override fun write(chunk: Chunk<out List<LocationDocument>?>) {
        chunk.items.forEach { documents -> locationIndexUseCase.indexLocationDocuments(documents) }
        println("TEST 완료")
    }
}
