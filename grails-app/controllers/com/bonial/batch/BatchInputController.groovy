package com.bonial.batch

import com.bonial.batch.interfaces.InputController
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.configuration.JobRegistry
import org.springframework.batch.core.launch.JobOperator

/**
 * batch-processor
 * @author  Konstantin Bork
 * @version 0.6
 * @created 08/28/2015
 *
 * Controller for incoming batch tasks.
 */

class BatchInputController implements InputController {

    static scope = "singleton"

    def priorityBatchProducerService
    def springBatchService
    def batchConsumerService
    def jobExecutionMapService

    def consumerThread = {
        while (true)
            batchConsumerService.consumeNextTask()
    }

    boolean isConsumerRunning = false

    def index() {
        if(!isConsumerRunning) {
            Thread.start(consumerThread)
            isConsumerRunning = true
        }
    }

    @Override
    def registerTask(def batchTaskName, def batchFile, def priority) {
        File temp = File.createTempFile("temp", ".txt")
        batchFile.transferTo(temp)
        priorityBatchProducerService.produceTask(batchTaskName, [file: "file:${temp.path}"], priority)
    }

    @Override
    def getStatus(def batchExecutionId) {
        JobExecution execution = jobExecutionMapService.getJobExecution(batchExecutionId)
        return execution.status
    }

    @Override
    def stopTask(def batchExecutionId) {
        JobOperator operator = springBatchService.jobOperator
        JobExecution execution = jobExecutionMapService.getJobExecution(batchExecutionId)
        operator.stop(execution.id)
    }

}