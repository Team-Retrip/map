package com.retrip.map.infra.adapter.`in`.batch

import com.retrip.map.application.`in`.service.EsSyncService
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
class LocationSyncTasklet(
    private val esSyncService: EsSyncService,
) : Tasklet {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        esSyncService.syncLocations()
        return RepeatStatus.FINISHED
    }
}
