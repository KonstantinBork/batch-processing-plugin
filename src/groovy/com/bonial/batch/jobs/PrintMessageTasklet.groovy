package com.bonial.batch.jobs

import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus

/**
 * batch-processor
 * @author Konstantin Bork
 * @version 1.0
 * @created 09/02/2015
 */

class PrintMessageTasklet implements Tasklet {

    String msg

    @Override
    RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        try {
            println(msg)
            Thread.sleep(10000)
            return RepeatStatus.FINISHED
        } catch(e) {
            log.warn("Error occured", e)
            return RepeatStatus.FINISHED
        }
    }

}