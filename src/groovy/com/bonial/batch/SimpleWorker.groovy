package com.bonial.batch

import com.bonial.batch.interfaces.Worker
import grails.util.Holders
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobParameter
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.integration.Message

/**
 * batch-processing-plugin
 * @author Konstantin Bork
 * @version 0.9
 * @created 08/31/2015
 *
 * Implementation of the Worker interface.
 */

class SimpleWorker implements Worker {

    def springBatchService = Holders.grailsApplication.mainContext.getBean("springBatchService")
    def jobMessageMapService = Holders.grailsApplication.mainContext.getBean("batchMapService")

    def currentTask
    def currentTaskExecutionId = -1

    @Override
    boolean start(Message taskMessage) {
        try {
            currentTask = taskMessage.payload
            Job job = currentTask.job
            JobParameters params = currentTask.jobParameters
            if (!params) {
                params = setJobParameters()
            }
            JobLauncher launcher = springBatchService.jobLauncher
            JobExecution execution = launcher.run(job, params)
            String id = jobMessageMapService.hashMessage(taskMessage)
            jobMessageMapService.addJobStatus(id, "EXECUTING")
            while(execution.status != BatchStatus.COMPLETED && execution.status != BatchStatus.STOPPED)
                Thread.sleep(1000)
            jobMessageMapService.addJobStatus(id, "FINISHED")
            return true
        } catch(e) {
            print("$e\n")
            return false
        }
    }

    @Override
    boolean stop() {
        if(!currentTask && currentTaskExecutionId < 0) return false
        try {
            springBatchService.stop(currentTaskExecutionId)
            currentTaskExecutionId = -1
            currentTask = null
            return true
        } catch(e) {
            print("$e\n")
            return false
        }
    }

    JobParameters setJobParameters() {
        def dateParam = new HashMap<String, JobParameter>()
        dateParam.put("date", new JobParameter(new Date()))
        return new JobParameters(dateParam)
    }

}