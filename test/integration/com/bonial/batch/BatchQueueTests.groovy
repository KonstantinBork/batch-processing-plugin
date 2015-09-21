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

class BatchQueueTests {

    BatchQueueService batchQueueService

    @Before
    void setUp() {

    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testEnqueue() {
        Message m = new GenericMessage("Hello World!")
        batchQueueService.queueChannel.clear()
        assert batchQueueService.queueChannel.queueSize == 0
        batchQueueService.enqueue(m)
        assert batchQueueService.queueChannel.queueSize == 1
    }

    @Test
    void testDequeue() {
        Message m = new GenericMessage("Hello World!")
        batchQueueService.queueChannel.clear()
        assert batchQueueService.queueChannel.queueSize == 0
        batchQueueService.enqueue(m)
        assert batchQueueService.queueChannel.queueSize == 1
        Message n = batchQueueService.dequeue()
        assert batchQueueService.queueChannel.queueSize == 0
        assert m == n
    }

    @Test
    void testSize() {
        batchQueueService.queueChannel.clear()
        assert batchQueueService.queueChannel.queueSize == 0
        assert batchQueueService.queueChannel.queueSize == batchQueueService.size()
        Message m = new GenericMessage("Hello World!")
        batchQueueService.enqueue(m)
        assert batchQueueService.queueChannel.queueSize == 1
        assert batchQueueService.queueChannel.queueSize == batchQueueService.size()
    }

    @Test
    void testIsEmpty() {
        batchQueueService.queueChannel.clear()
        assert batchQueueService.queueChannel.queueSize == 0
        assert batchQueueService.isEmpty()
    }

}