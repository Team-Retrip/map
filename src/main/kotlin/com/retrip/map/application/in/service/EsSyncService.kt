package com.retrip.map.application.`in`.service

import com.retrip.map.application.out.repository.LocationDetailElasticRepository
import com.retrip.map.application.out.repository.LocationDetailRepository
import com.retrip.map.application.out.repository.LocationElasticRepository
import com.retrip.map.application.out.repository.LocationRepository
import com.retrip.map.infra.adapter.out.search.elasticsearch.entity.LocationDetailDocument
import com.retrip.map.infra.adapter.out.search.elasticsearch.entity.LocationDocument
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicReference

@Service
class EsSyncService(
    private val locationDetailRepository: LocationDetailRepository,
    private val locationRepository: LocationRepository,
    private val locationDetailElasticRepository: LocationDetailElasticRepository,
    private val locationElasticRepository: LocationElasticRepository,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    // LocalDateTime.MIN = 앱 최초 기동 시 전체 색인
    private val lastLocationDetailSync = AtomicReference(LocalDateTime.MIN)
    private val lastLocationSync = AtomicReference(LocalDateTime.MIN)

    @Transactional(readOnly = true)
    fun syncLocationDetails() {
        val syncStart = LocalDateTime.now()
        val lastSync = lastLocationDetailSync.get()

        // 신규/수정: 마지막 동기화 이후 editedAt이 변경된 레코드
        val toUpsert = if (lastSync == LocalDateTime.MIN) {
            locationDetailRepository.findAll()
        } else {
            locationDetailRepository.findByEditedAtAfter(lastSync)
        }
        if (toUpsert.isNotEmpty()) {
            val docs = toUpsert.mapNotNull { entity ->
                runCatching { LocationDetailDocument.of(entity) }.getOrElse { e ->
                    log.warn("[ES Sync] LocationDetail 변환 실패: id={}", entity.id, e)
                    null
                }
            }
            locationDetailElasticRepository.saveAll(docs)
        }

        // 삭제: ES에 있지만 DB에 없는 문서 제거
        val dbIds = locationDetailRepository.findAllIds().toSet()
        val toDelete = locationDetailElasticRepository.findAll()
            .map { it.id }
            .filter { it !in dbIds }
        if (toDelete.isNotEmpty()) {
            locationDetailElasticRepository.deleteAllById(toDelete)
        }

        lastLocationDetailSync.set(syncStart)
        log.info("[ES Sync] LocationDetail - 색인: {}건, 삭제: {}건", toUpsert.size, toDelete.size)
    }

    @Transactional(readOnly = true)
    fun syncLocations() {
        val syncStart = LocalDateTime.now()
        val lastSync = lastLocationSync.get()

        val toUpsert = if (lastSync == LocalDateTime.MIN) {
            locationRepository.findAll()
        } else {
            locationRepository.findByEditedAtAfter(lastSync)
        }
        if (toUpsert.isNotEmpty()) {
            val docs = toUpsert.mapNotNull { entity ->
                runCatching { LocationDocument.of(entity) }.getOrElse { e ->
                    log.warn("[ES Sync] Location 변환 실패: id={}", entity.id, e)
                    null
                }
            }
            locationElasticRepository.saveAll(docs)
        }

        val dbIds = locationRepository.findAllIds().toSet()
        val toDelete = locationElasticRepository.findAll()
            .map { it.id }
            .filter { it !in dbIds }
        if (toDelete.isNotEmpty()) {
            locationElasticRepository.deleteAllById(toDelete)
        }

        lastLocationSync.set(syncStart)
        log.info("[ES Sync] Location - 색인: {}건, 삭제: {}건", toUpsert.size, toDelete.size)
    }
}
