package com.bonial.batch

import com.bonial.batch.interfaces.InputController
import org.springframework.batch.core.configuration.JobRegistry
import org.springframework.batch.core.launch.JobOperator

/**
 * batch-processor
 * @author  Konstantin Bork
 * @version 0.5
 * @created 08/28/2015
 *
 * Controller for incoming batch tasks.
 */

class BatchInputController implements InputController {

    def batchProducerService
    def springBatchService
    def batchConsumerService

    boolean consumingRunning = false

    def index() {
        if(!consumingRunning) {
            Thread.start {
                while(true)
                    batchConsumerService.consumeNextTask()
            }
            consumingRunning = true
        }
        Map lists = prepareLists()
        render(view: "index", model: lists)
    }

    @Override
    def registerTask(def batchTaskName, def batchFile) {
        File temp = File.createTempFile("temp", ".txt")
        batchFile.transferTo(temp)
        batchProducerService.produceTask(batchTaskName, [file: "file:${temp.path}"])
        redirect(action: index())
    }

    @Override
    def getStatus(def batchExecutionId) {
        redirect(controller: "springBatchJobExecution", action: "show", id: batchExecutionId)
    }

    @Override
    def stopTask(def batchExecutionId) {
        JobOperator operator = springBatchService.jobOperator
        operator.stop(batchExecutionId)
        redirect(action: index())
    }

    private Map prepareLists() {
        JobRegistry registry = springBatchService.jobRegistry
        Collection<String> jobs = registry.jobNames
        JobOperator operator = springBatchService.jobOperator
        Map runningExecutions = [:]
        for(String job in jobs) {
            Set<Long> execs = operator.getRunningExecutions(job)
            runningExecutions.put(job, execs)
        }
        return [jobNames: jobs, executions: runningExecutions]
    }

}