package com.bonial.batch

import com.bonial.batch.interfaces.Queue
import org.springframework.integration.Message
import org.springframework.integration.channel.QueueChannel

/**
 * batch-processor
 * @author  Konstantin Bork
 * @version 0.9
 * @created 08/28/2015
 *
 * The implementation of the Queue interface.
 */

class BatchQueueService implements Queue {

    def batchMapService

    static int QUEUE_SIZE = 1000
    def queueChannel = new QueueChannel(QUEUE_SIZE)

    @Override
    boolean enqueue(Message message) {
        if(queueChannel.remainingCapacity == 0)
            return false
        def sent = queueChannel.send(message)
        String id = batchMapService.hashMessage(message)
        batchMapService.addJobStatus(id, "QUEUED")
        return sent
    }

    @Override
    Message dequeue() {
        Message m = queueChannel.receive()
        return m
    }

    @Override
    int size() {
        return queueChannel.queueSize
    }

    @Override
    boolean isEmpty() {
        return size() == 0
    }

}