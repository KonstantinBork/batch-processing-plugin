package com.bonial.batch.interfaces

/**
 * batch-processing-plugin
 * @author Konstantin Bork
 * @version 0.6
 * @created 08/28/2015
 *
 * Interface for the input controller.
 */

interface InputController {

    /**
     * Registers a new batch task to the queue.
     */
    def registerTask(def batchTaskName, def batchFile, def priority)

    /**
     * Gets the current status of the given batch task.
     */
    def getStatus(def batchExecutionId)

    /**
     * Requests to stop the given batch task.
     */
    def stopTask(def batchExecutionId)

}