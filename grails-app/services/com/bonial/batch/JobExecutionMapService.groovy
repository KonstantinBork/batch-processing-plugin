package com.bonial.batch

import org.springframework.batch.core.JobExecution

/**
 * batch-processing-plugin
 * @author Konstantin Bork
 * @version 0.1
 * @created 09/07/2015
 *
 * A service which saves all executions in memory.
 */

class JobExecutionMapService {

    Map<Long, JobExecution> jobExecutions = new HashMap()

    void addJobExection(long id, JobExecution execution) {
        jobExecutions.put(id, execution)
    }

    JobExecution getJobExecution(long id) {
        return jobExecutions.get(id)
    }

}