package com.bonial.batch

import com.bonial.batch.interfaces.Queue
import org.springframework.integration.Message
import org.springframework.integration.channel.PriorityChannel

/**
 * batch-processor
 * @author  Konstantin Bork
 * @version 0.6
 * @created 09/03/2015
 *
 * Another implementation of the Queue interface.
 */

class PriorityBatchQueueService implements Queue {

    static int QUEUE_SIZE = 1000
    def priorityQueueChannel = new PriorityChannel(QUEUE_SIZE)

    @Override
    boolean enqueue(Message message) {
        if(priorityQueueChannel.remainingCapacity == 0)
            return false
        def sent = priorityQueueChannel.send(message)
        return sent
    }

    @Override
    Message dequeue() {
        Message m = priorityQueueChannel.receive()
        return m
    }

    @Override
    int size() {
        return priorityQueueChannel.queueSize
    }

    @Override
    boolean isEmpty() {
        return size() == 0
    }

}