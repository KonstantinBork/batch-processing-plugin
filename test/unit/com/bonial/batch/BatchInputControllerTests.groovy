package com.bonial.batch

import grails.plugins.springbatch.SpringBatchService
import grails.test.mixin.*
import org.apache.commons.fileupload.disk.DiskFileItem
import org.springframework.batch.core.BatchStatus
import org.springframework.web.multipart.commons.CommonsMultipartFile

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */

@TestFor(BatchInputController)
class BatchInputControllerTests {

    def batchConsumerService
    def batchMapService
    def priorityBatchQueueService
    def springBatchService

    void testIndex() {
        defineBeans {
            springBatchService(SpringBatchService)
            batchConsumerService(BatchConsumerService)
            batchMapService(BatchMapService)
            priorityBatchQueueService(PriorityBatchQueueService)
        }
        assert !controller.isConsumerRunning
        controller.index()
        assert controller.isConsumerRunning
    }

    void testRegisterTask() {
        defineBeans {
            springBatchService(SpringBatchService)
            priorityBatchQueueService(PriorityBatchQueueService)
        }
        assert priorityBatchQueueService.size() == 0
        File f = new File("/Users/konstantin/.grails/2.2.4/projects/BatchPluginTest/tomcat/work/Tomcat/localhost/BatchPluginTest")
        DiskFileItem dfi = new DiskFileItem("file", "application/octet-stream", false, "", 10240, f)
        CommonsMultipartFile cmp = new CommonsMultipartFile(dfi)
        controller.registerTask("lJob", cmp, "0")
        assert priorityBatchQueueService.size() == 1
    }

    void testGetStatus() {
        defineBeans {
            springBatchService(SpringBatchService)
            batchConsumerService(BatchConsumerService)
            batchMapService(BatchMapService)
        }
        File f = new File("/Users/konstantin/.grails/2.2.4/projects/BatchPluginTest/tomcat/work/Tomcat/localhost/BatchPluginTest")
        DiskFileItem dfi = new DiskFileItem("file", "application/octet-stream", false, "", 10240, f)
        CommonsMultipartFile cmp = new CommonsMultipartFile(dfi)
        controller.registerTask("lJob", cmp, "0")
        String hash = batchMapService.jobMessages.keySet()[0]
        assert controller.getStatus(hash) == BatchStatus.STARTED.toString()
        Thread.sleep(31000)
        assert controller.getStatus(hash) == BatchStatus.COMPLETED.toString()
    }

    void testStopTask() {
        defineBeans {
            springBatchService(SpringBatchService)
            batchConsumerService(BatchConsumerService)
            batchMapService(BatchMapService)
        }
        File f = new File("/Users/konstantin/.grails/2.2.4/projects/BatchPluginTest/tomcat/work/Tomcat/localhost/BatchPluginTest")
        DiskFileItem dfi = new DiskFileItem("file", "application/octet-stream", false, "", 10240, f)
        CommonsMultipartFile cmp = new CommonsMultipartFile(dfi)
        controller.registerTask("lJob", cmp, "0")
        String hash = batchMapService.jobMessages.keySet()[0]
        assert controller.getStatus(hash) == BatchStatus.STARTED.toString()
        controller.stopTask(hash)
        assert controller.getStatus(hash) == BatchStatus.STOPPING.toString()
        Thread.sleep(31000)
        assert controller.getStatus(hash) == BatchStatus.STOPPED.toString()
    }

}