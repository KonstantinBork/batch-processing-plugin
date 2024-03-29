package com.bonial.batch

import org.junit.*
import org.springframework.integration.Message
import org.springframework.integration.message.GenericMessage

/**
 * batch-processor
 * @author  Konstantin Bork
 * @version 0.9
 * @created 09/10/2015
 */

class BatchMapTests {

    BatchMapService batchMapService

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testAddJobMessage() {
        batchMapService.jobMessages.clear()
        assert batchMapService.jobMessages.size() == 0
        String id = "1"
        Message m = new GenericMessage("Hello World")
        batchMapService.addJobMessage(id, m)
        assert batchMapService.jobMessages.size() == 1
    }

    @Test
    void testAddJobStatus() {
        batchMapService.jobStatus.clear()
        assert batchMapService.jobStatus.size() == 0
        String id = "1"
        String status = "EXECUTING"
        batchMapService.addJobStatus(id, status)
        assert batchMapService.jobStatus.size() == 1
    }

    @Test
    void testGetJobMessage() {
        batchMapService.jobMessages.clear()
        assert batchMapService.jobMessages.size() == 0
        String id = "1"
        Message m = new GenericMessage("Hello World")
        batchMapService.addJobMessage(id, m)
        assert batchMapService.jobMessages.size() == 1
        Message n = batchMapService.getJobMessage(id)
        assert m == n
    }

    @Test
    void testGetJobStatus() {
        batchMapService.jobStatus.clear()
        assert batchMapService.jobStatus.size() == 0
        String id = "1"
        String status = "EXECUTING"
        batchMapService.addJobStatus(id, status)
        assert batchMapService.jobStatus.size() == 1
        String n = batchMapService.getJobStatus(id)
        assert status == n
    }

    @Test
    void testHashing() {
        Message m = new GenericMessage("Hello World!")
        Message n = new GenericMessage("Hello World!")
        String mHash = batchMapService.hashMessage(m)
        String nHash = batchMapService.hashMessage(n)
        assert mHash != nHash
    }

}