package com.bonial.batch

import org.springframework.integration.Message

import java.security.MessageDigest

/**
 * batch-processing-plugin
 * @author Konstantin Bork
 * @version 0.9
 * @created 09/07/2015
 *
 * A service which saves all incoming tasks in memory.
 * ~ TODO database connection
 */

class BatchMapService {

    Map<String, Message> jobMessages
    Map<String, String> jobStatus

    BatchMapService() {
        jobMessages = new HashMap<>()
        jobStatus = new HashMap<>()
    }

    /**
     * Adds a job message to the messages map.
     *
     * @param id unique identifier for the JobExecution
     * @param message the job message to save
     */
    void addJobMessage(String id, Message message) {
        jobMessages.put(id, message)
    }

    /**
     * Adds the status of the job to the status map.
     *
     * @param id the id of the job
     * @param status the current status of the job
     */
    void addJobStatus(String id, String status) {
        jobStatus.put(id, status)
    }

    /**
     * Get the job for the specific id.
     *
     * @param id identifier for the JobExecution to find
     * @return the JobExecution for the given id
     */
    Message getJobMessage(String id) {
        return jobMessages.get(id)
    }

    /**
     * Get the current status of the job with the given id.
     *
     * @param id id of the job
     * @return status of the given job
     */
    String getJobStatus(String id) {
        return jobStatus.get(id)
    }

    /**
     * Get the MD5 hash of the given message.
     *
     * @param m message to find a hash for
     * @return hash value of the given message
     */
    String hashMessage(Message m) {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5")
        try {
            byte[] digest = messageDigest.digest(m.toString().getBytes())
            BigInteger number = new BigInteger(1, digest)
            String hashtext = number.toString(16)
            return hashtext
        } catch(e) {

        }
        return m.hashCode()
    }

}