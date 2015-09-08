package com.bonial.batch

import com.bonial.batch.interfaces.Producer
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.configuration.JobRegistry
import org.springframework.batch.integration.launch.JobLaunchRequest
import org.springframework.integration.Message
import org.springframework.integration.message.GenericMessage

/**
 * batch-processing-plugin
 * @author  Konstantin Bork
 * @version 0.6
 * @created 09/03/2015
 *
 * Another implementation of the Queue interface.
 */

class PriorityBatchProducerService implements Producer {

    def priorityBatchQueueService
    def batchMapService
    def springBatchService

    @Override
    void produceTask(String jobName, Map params, String priority = "0") {
        Job job = findJob(jobName)
        JobLaunchRequest launchRequest = buildLaunchRequest(job, params)
        Message m = new GenericMessage(launchRequest, [priority: Integer.parseInt(priority)])
        String hash = batchMapService.hashMessage(m)
        batchMapService.addJobMessage(hash, m)
        batchMapService.addJobStatus(hash, "CREATED")
        priorityBatchQueueService.enqueue(m)
    }

    Job findJob(String jobName) {
        JobRegistry registry = springBatchService.jobRegistry
        return registry.getJob(jobName)
    }

    JobLaunchRequest buildLaunchRequest(Job job, Map params) {
        JobParameters jobParameters = buildJobParameters(params)
        return new JobLaunchRequest(job, jobParameters)
    }

    JobParameters buildJobParameters(Map params) {
        JobParametersBuilder parametersBuilder = new JobParametersBuilder()
        if(!params) {
            parametersBuilder.addDate("Date", new Date())
        }
        else {
            for (def parameter in params)
                parametersBuilder.addString(parameter.key, parameter.value)
        }
        return parametersBuilder.toJobParameters()
    }

}