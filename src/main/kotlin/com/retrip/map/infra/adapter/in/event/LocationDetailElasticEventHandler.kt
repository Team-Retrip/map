package com.retrip.map.infra.adapter.`in`.event

import com.retrip.map.application.out.repository.LocationDetailElasticRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class LocationDetailElasticEventHandler(
    private val locationDetailElasticRepository: LocationDetailElasticRepository,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handle(event: LocationDetailElasticEvent) {
        try {
            when (event) {
                is LocationDetailElasticEvent.Created ->
                    locationDetailElasticRepository.save(event.document)
                is LocationDetailElasticEvent.Updated -> {
                    locationDetailElasticRepository.deleteById(event.id)
                    locationDetailElasticRepository.save(event.document)
                }
                is LocationDetailElasticEvent.Deleted ->
                    locationDetailElasticRepository.deleteById(event.id)
            }
        } catch (e: Exception) {
            log.error("[ES Sync Failed] DB와 ES 동기화 실패. event={}", event, e)
        }
    }
}
