package com.bonial.batch

import com.bonial.batch.interfaces.Consumer
import com.bonial.batch.interfaces.Worker
import org.springframework.integration.Message

import java.util.concurrent.ConcurrentLinkedQueue

/**
 * batch-processor
 * @author  Konstantin Bork
 * @version 0.5
 * @created 08/28/2015
 *
 * The implementation of the Consumer interface.
 */

class BatchConsumerService implements Consumer {

    def batchQueueService

    static final int MAX_WORKERS = 15                                               // number of workers
    ConcurrentLinkedQueue<Worker> availableWorkers = new ConcurrentLinkedQueue<>()  // list with all available workers
    ConcurrentLinkedQueue<Worker> busyWorkers = new ConcurrentLinkedQueue<>()       // list with all busy workers

    BatchConsumerService() {
        for(int i = 0; i < MAX_WORKERS; i++)
            availableWorkers.add(new SimpleWorker())
    }

    @Override
    void consumeNextTask() {
        Message m = batchQueueService.dequeue()
        Thread.start {
            runTask(m)
        }
    }

    void runTask(Message m) {
        Worker w = availableWorkers.poll()
        while(!w) {
            w = availableWorkers.poll()
        }
        busyWorkers.add(w)
        w.start(m)
        busyWorkers.remove(w)
        availableWorkers.add(w)
        Thread.sleep(new Random().nextInt(100))
    }

}