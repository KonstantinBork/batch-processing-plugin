package com.bonial.batch.jobs

import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus

/**
 * batch-processor
 * @author Konstantin Bork
 * @version 1.0
 * @created 09/01/2015
 *
 * Example for the look of a tasklet.
 */

class LongJobTasklet implements Tasklet {

    @Override
    RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        try {
            println("This is a long lasting job")
            def seconds = 100
            Thread.sleep(seconds * 1000)
            println("Job finished")
            return RepeatStatus.FINISHED
        } catch(e) {
            log.warn("Error occured", e)
            return RepeatStatus.FINISHED
        }
    }

}