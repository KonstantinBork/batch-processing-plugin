package com.bonial.batch.interfaces

import org.springframework.integration.Message

/**
 * batch-processing-plugin
 * @author Konstantin Bork
 * @version 0.9
 * @created 08/31/2015
 *
 * An interface for the workers which run the tasks.
 */

interface Worker {

    /**
     * Starts the given task.
     * @param taskMessage
     * @return true if task is started
     */
    boolean start(Message taskMessage)

    /**
     * Stops the current task.
     * @return true if the task is stopped
     */
    boolean stop()

}