package com.bonial.batch

import com.bonial.batch.interfaces.InputController
import grails.util.Holders
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobOperator
import org.springframework.batch.core.repository.JobRepository
import org.springframework.integration.Message

/**
 * batch-processor
 * @author  Konstantin Bork
 * @version 0.8
 * @created 08/28/2015
 *
 * Controller for incoming batch tasks.
 */

class BatchInputController implements InputController {

    static scope = "singleton"

    def priorityBatchProducerService
    def springBatchService
    def batchConsumerService
    def batchMapService

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
    void registerTask(String batchTaskName, def batchFile, String priority) {
        File temp = File.createTempFile("temp", ".txt")
        batchFile.transferTo(temp)
        priorityBatchProducerService.produceTask(batchTaskName, [file: "file:${temp.path}"], priority)
    }

    @Override
    String getStatus(String batchId) {
        return batchMapService.getJobStatus(batchId)
    }

    @Override
    void stopTask(String batchId) {
        JobOperator operator = springBatchService.jobOperator
        if(batchMapService.getJobStatus(batchExecutionId) != "EXECUTING") return
        JobExecution execution = getLastExecution(batchExecutionId)
        operator.stop(execution.id)
        batchMapService.addJobStatus(batchExecutionId, "STOPPED")
    }

    JobExecution getLastExecution(String id) {
        Message message = batchMapService.getJobMessage(id)
        String jobName = message.payload.job.name
        JobParameters params = message.payload.jobParameters
        JobRepository repository = Holders.grailsApplication.mainContext.getBean("jobRepository")
        return repository.getLastJobExecution(jobName, params)
    }

}