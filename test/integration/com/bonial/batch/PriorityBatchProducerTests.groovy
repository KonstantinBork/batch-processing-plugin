package com.bonial.batch

import org.junit.*

/**
 * batch-processor
 * @author  Konstantin Bork
 * @version 0.9
 * @created 09/10/2015
 */

class PriorityBatchProducerTests {

    PriorityBatchProducerService priorityBatchProducerService
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
    void testProduceTask() {
        String jobName = "longJob"
        Map params = null
        String priority = "0"
        assert priorityBatchQueueService.isEmpty()
        priorityBatchProducerService.produceTask(jobName, params, priority)
        assert priorityBatchQueueService.size() == 1
    }

}