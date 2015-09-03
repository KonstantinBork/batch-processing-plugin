package com.bonial.batch.interfaces

import org.springframework.integration.Message

/**
 * batch-processing-plugin
 * @author Konstantin Bork
 * @version 0.2
 * @created 08/28/2015
 *
 * Interface for the batch task queue.
 */

interface Queue {

    /**
     * Puts the given batch task into the queue.
     */
    boolean enqueue(Message message)

    /**
     * Gets the first batch task from the queue and removes it.
     */
    Message dequeue()

    /**
     * Gets the current size of the queue.
     * @return the size of the queue
     */
    int size()

    /**
     * Asks if queue is empty or not.
     * @return if queue is empty
     */
    boolean isEmpty()

}