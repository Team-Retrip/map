package com.retrip.map.infra.adapter.`in`.batch

import com.retrip.map.domain.entity.Location
import com.retrip.map.infra.adapter.out.search.elasticsearch.entity.LocationDocument
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
//@EnableBatchProcessing
class MapBatchConfig(
    @Value("\${spring.batch.job.name}")
    private val jobName: String,
    private val reader: MapReader,
    private val processor: MapProcessor,
    private val writer: MapWriter,
) {

    @Bean
    fun mapJob(
        jobRepository: JobRepository,
        mapStep: Step
    ): Job {
        return JobBuilder(jobName, jobRepository)
            .start(mapStep)
            .build()

    }

    @Bean
    fun mapStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager
    ): Step {
        return StepBuilder("map-step", jobRepository)
            .chunk<List<Location>, List<LocationDocument>>(10, transactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build()
    }


}
