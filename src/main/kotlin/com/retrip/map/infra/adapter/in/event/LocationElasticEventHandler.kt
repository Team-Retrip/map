package com.retrip.map.infra.adapter.`in`.event

import com.retrip.map.application.out.repository.LocationElasticRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class LocationElasticEventHandler(
    private val locationElasticRepository: LocationElasticRepository,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handle(event: LocationElasticEvent) {
        try {
            when (event) {
                is LocationElasticEvent.Created ->
                    locationElasticRepository.save(event.document)
                is LocationElasticEvent.Updated -> {
                    locationElasticRepository.deleteById(event.id)
                    locationElasticRepository.save(event.document)
                }
                is LocationElasticEvent.Deleted ->
                    locationElasticRepository.deleteById(event.id)
            }
        } catch (e: Exception) {
            log.error("[ES Sync Failed] DB와 ES 동기화 실패. event={}", event, e)
        }
    }
}
