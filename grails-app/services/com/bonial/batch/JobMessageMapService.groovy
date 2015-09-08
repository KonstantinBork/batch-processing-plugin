package com.bonial.batch

import org.springframework.integration.Message

/**
 * batch-processing-plugin
 * @author Konstantin Bork
 * @version 0.3
 * @created 09/07/2015
 *
 * A service which saves all executions in memory.
 */

class JobMessageMapService {

    Map<String, Message> jobMessages
    Map<String, String> jobStatus

    JobMessageMapService() {
        jobMessages = new HashMap<>()
        jobStatus = new HashMap<>()
    }

    /**
     * Adds a job message to the messages map.
     *
     * @param id unique identifier for the JobExecution
     * @param message the job message to save
     */
    void addJobMessage(long id, Message message) {
        String idString = id.toString()
        jobMessages.put(idString, message)
    }

    /**
     * Adds the status of the job to the status map.
     *
     * @param id the id of the job
     * @param status the current status of the job
     */
    void addJobStatus(long id, String status) {
        String idString = id.toString()
        jobStatus.put(idString, status)
    }

    /**
     * Get the job for the specific id.
     *
     * @param id identifier for the JobExecution to find
     * @return the JobExecution for the given id
     */
    Message getJobMessage(long id) {
        return jobMessages.get(id)
    }

    /**
     * Get the current status of the job with the given id.
     *
     * @param id id of the job
     * @return status of the given job
     */
    String getJobStatus(long id) {
        return jobStatus.get(id)
    }

    long hashMessage(Message m) {
        return m.hashCode()
    }

}