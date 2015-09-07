package com.bonial.batch

import org.springframework.integration.Message

/**
 * batch-processing-plugin
 * @author Konstantin Bork
 * @version 0.2
 * @created 09/07/2015
 *
 * A service which saves all executions in memory.
 */

class JobMessageMapService {

    Map<String, Message> jobMessages = new HashMap()
    Map<String, String> jobStatus = new HashMap()

    /**
     * Adds a job message to the map.
     * @param id unique identifier for the JobExecution
     * @param message the job message to save
     */
    void addJobMessage(long id, Message message) {
        String idString = id.toString()
        jobMessages.put(idString, message)
    }

    void addJobStatus(long id, String status) {
        String idString = id.toString()
        jobStatus.put(idString, status)
    }

    /**
     * Get the JobExecution for the specific id.
     * @param id identifier for the JobExecution to find
     * @return the JobExecution for the given id
     */
    Message getJobMessage(long id) {
        return jobMessages.get(id)
    }

    String getJobStatus(long id) {
        return jobStatus.get(id)
    }

    long hashMessage(Message m) {
        return m.hashCode()
    }

}