package com.retrip.map.infra.adapter.`in`.batch

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class EsSyncScheduler(
    private val jobLauncher: JobLauncher,
    @Qualifier("locationDetailSyncJob") private val locationDetailSyncJob: Job,
    @Qualifier("locationSyncJob") private val locationSyncJob: Job,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @Scheduled(fixedDelay = 3 * 60 * 1000)
    fun syncLocationDetails() {
        runJob(locationDetailSyncJob, "location-detail-sync-job")
    }

    @Scheduled(fixedDelay = 3 * 60 * 1000)
    fun syncLocations() {
        runJob(locationSyncJob, "location-sync-job")
    }

    private fun runJob(job: Job, name: String) {
        try {
            val params = JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters()
            jobLauncher.run(job, params)
        } catch (e: Exception) {
            log.error("[ES Sync] {} 실행 실패", name, e)
        }
    }
}
