package com.bonial.batch

import org.springframework.integration.Message
import org.springframework.integration.message.GenericMessage

import org.junit.*

/**
 * batch-processor
 * @author  Konstantin Bork
 * @version 0.9
 * @created 09/10/2015
 */

class PriorityBatchQueueTests {

    PriorityBatchQueueService priorityBatchQueueService

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testEnqueue() {
        Message m = new GenericMessage("Hello World!")
        priorityBatchQueueService.priorityQueueChannel.clear()
        assert priorityBatchQueueService.priorityQueueChannel.queueSize == 0
        priorityBatchQueueService.enqueue(m)
        assert priorityBatchQueueService.priorityQueueChannel.queueSize == 1
    }

    @Test
    void testDequeue() {
        Message m = new GenericMessage("Hello World!")
        priorityBatchQueueService.priorityQueueChannel.clear()
        assert priorityBatchQueueService.priorityQueueChannel.queueSize == 0
        priorityBatchQueueService.enqueue(m)
        assert priorityBatchQueueService.priorityQueueChannel.queueSize == 1
        Message n = priorityBatchQueueService.dequeue()
        assert priorityBatchQueueService.priorityQueueChannel.queueSize == 0
        assert m == n
    }

    @Test
    void testSize() {
        priorityBatchQueueService.priorityQueueChannel.clear()
        assert priorityBatchQueueService.priorityQueueChannel.queueSize == 0
        assert priorityBatchQueueService.priorityQueueChannel.queueSize == priorityBatchQueueService.size()
        Message m = new GenericMessage("Hello World!")
        priorityBatchQueueService.enqueue(m)
        assert priorityBatchQueueService.priorityQueueChannel.queueSize == 1
        assert priorityBatchQueueService.priorityQueueChannel.queueSize == priorityBatchQueueService.size()
    }

    @Test
    void testIsEmpty() {
        priorityBatchQueueService.priorityQueueChannel.clear()
        assert priorityBatchQueueService.priorityQueueChannel.queueSize == 0
        assert priorityBatchQueueService.isEmpty()
    }

}
