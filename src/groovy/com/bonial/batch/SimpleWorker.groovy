package com.bonial.batch

import com.bonial.batch.interfaces.Worker
import grails.util.Holders
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobParameter
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.integration.Message

/**
 * batch-processing-plugin
 * @author Konstantin Bork
 * @version 0.6
 * @created 08/31/2015
 *
 * Implementation of the Worker interface.
 */

class SimpleWorker implements Worker {

    def springBatchService = Holders.grailsApplication.mainContext.getBean("springBatchService")
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
            currentTaskExecutionId = execution.jobId
            while(execution.exitStatus.exitCode == "EXECUTING")
                Thread.sleep(1000)
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