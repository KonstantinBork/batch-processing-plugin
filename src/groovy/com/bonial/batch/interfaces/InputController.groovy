package com.bonial.batch.interfaces

/**
 * batch-processing-plugin
 * @author Konstantin Bork
 * @version 0.9
 * @created 08/28/2015
 *
 * Interface for the input controller.
 */

interface InputController {

    /**
     * Registers a new batch task to the queue.
     */
    void registerTask(String batchTaskName, def batchFile, String priority)

    /**
     * Gets the current status of the given batch task.
     */
    String getStatus(String batchId)

    /**
     * Requests to stop the given batch task.
     */
    void stopTask(String batchId)

}