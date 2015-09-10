package com.bonial.batch

import org.junit.*

class BatchProducerTests {

    BatchProducerService batchProducerService
    BatchQueueService batchQueueService

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
        assert batchQueueService.isEmpty()
        batchProducerService.produceTask(jobName, params, priority)
        assert batchQueueService.size() == 1
    }

}