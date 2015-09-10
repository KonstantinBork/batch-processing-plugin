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

class BatchConsumerTests {

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
    void testConsumeNextTask() {
        assert priorityBatchQueueService.isEmpty()
        Message m = new GenericMessage<>("Hello World!")
        priorityBatchQueueService.enqueue(m)
        assert priorityBatchQueueService.size() == 1
        Message n = priorityBatchQueueService.dequeue()
        assert m == n
    }

}