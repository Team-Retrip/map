package com.retrip.map.infra.adapter.`in`.batch

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class MapBatchConfig(
    private val locationDetailSyncTasklet: LocationDetailSyncTasklet,
    private val locationSyncTasklet: LocationSyncTasklet,
) {

    @Bean
    fun locationDetailSyncJob(jobRepository: JobRepository, locationDetailSyncStep: Step): Job =
        JobBuilder("location-detail-sync-job", jobRepository)
            .incrementer(RunIdIncrementer())
            .start(locationDetailSyncStep)
            .build()

    @Bean
    fun locationDetailSyncStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
    ): Step =
        StepBuilder("location-detail-sync-step", jobRepository)
            .tasklet(locationDetailSyncTasklet, transactionManager)
            .build()

    @Bean
    fun locationSyncJob(jobRepository: JobRepository, locationSyncStep: Step): Job =
        JobBuilder("location-sync-job", jobRepository)
            .incrementer(RunIdIncrementer())
            .start(locationSyncStep)
            .build()

    @Bean
    fun locationSyncStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
    ): Step =
        StepBuilder("location-sync-step", jobRepository)
            .tasklet(locationSyncTasklet, transactionManager)
            .build()
}
