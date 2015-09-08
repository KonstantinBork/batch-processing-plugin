package com.bonial.batch


import grails.test.mixin.*
import grails.util.Holders
import org.springframework.integration.Message
import org.springframework.integration.message.GenericMessage

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(BatchMapService)
class BatchMapServiceTests {

    def batchMapService = Holders.grailsApplication.mainContext.getBean("batchMapService")

    void "testInput"() {
        assertEquals(0, batchMapService.jobMessages.size())
        Message m = new GenericMessage("Hello World")
        batchMapService.addJobMessage(1, m)
        assertEquals(1, batchMapService.jobMessages.size())
    }

}
