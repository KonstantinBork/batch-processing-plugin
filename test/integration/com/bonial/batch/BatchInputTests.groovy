package com.bonial.batch

import org.junit.*

class BatchInputTests {

    BatchInputController batchInputController = new BatchInputController()

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testRegisterTask() {
        batchInputController.index()
        String jobName = "longJob"
        Map params = null
        String priority = "0"
        batchInputController.batchMapService.jobMessages.clear()
        assert  batchInputController.batchMapService.jobMessages.size() == 0
        batchInputController.registerTask(jobName, params, priority)
        assert  batchInputController.batchMapService.jobMessages.size() == 1
    }

    @Test
    void testGetStatus() {
        batchInputController.index()
        String jobName = "longJob"
        Map params = null
        String priority = "0"
        batchInputController.batchMapService.jobMessages.clear()
        assert  batchInputController.batchMapService.jobMessages.size() == 0
        batchInputController.registerTask(jobName, params, priority)
        assert  batchInputController.batchMapService.jobMessages.size() == 1
        String batchId = batchInputController.batchMapService.jobMessages.keySet().toArray()[0]
        Thread.sleep(5000)
        String status = batchInputController.getStatus(batchId)
        assert status == "EXECUTING"
    }

    @Test
    void testStopTask() {
        batchInputController.index()
        String jobName = "longJob"
        Map params = null
        String priority = "0"
        batchInputController.batchMapService.jobMessages.clear()
        assert  batchInputController.batchMapService.jobMessages.size() == 0
        batchInputController.registerTask(jobName, params, priority)
        assert  batchInputController.batchMapService.jobMessages.size() == 1
        String batchId = batchInputController.batchMapService.jobMessages.keySet().toArray()[0]
        Thread.sleep(5000)
        String status = batchInputController.getStatus(batchId)
        assert status == "EXECUTING"
        batchInputController.stopTask(batchId)
        Thread.sleep(31000)
        status = batchInputController.getStatus(batchId)
        assert status == "STOPPED"
    }

}