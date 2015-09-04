package com.bonial.batch

import com.bonial.batch.interfaces.Consumer
import com.bonial.batch.interfaces.Worker
import org.springframework.integration.Message

import java.util.concurrent.ConcurrentLinkedQueue

/**
 * batch-processor
 * @author  Konstantin Bork
 * @version 0.6
 * @created 08/28/2015
 *
 * The implementation of the Consumer interface.
 */

class BatchConsumerService implements Consumer {

    static transactional = false

    def priorityBatchQueueService

    static final Random RANDOM_OBJECT = new Random()
    static final int MAX_WAINTING_MILLISECONDS = 10000

    static final int MAX_WORKERS = 5                                                // number of workers
    ConcurrentLinkedQueue<Worker> availableWorkers = new ConcurrentLinkedQueue<>()  // list with all available workers
    ConcurrentLinkedQueue<Worker> busyWorkers = new ConcurrentLinkedQueue<>()       // list with all busy workers

    BatchConsumerService() {
        for(int i = 0; i < MAX_WORKERS; i++)
            availableWorkers.add(new SimpleWorker())
    }

    @Override
    void consumeNextTask() {
        Message m = priorityBatchQueueService.dequeue()
        Worker w = getWorker()
        Thread.start {
            runTask(w, m)
        }
    }

    Worker getWorker() {
        Worker w = availableWorkers.poll()
        while(!w) {
            w = availableWorkers.poll()
        }
        busyWorkers.add(w)
        return w
    }

    void runTask(Worker w, Message m) {
        w.start(m)
        busyWorkers.remove(w)
        availableWorkers.add(w)
        Thread.sleep(RANDOM_OBJECT.nextInt(MAX_WAINTING_MILLISECONDS))
    }

}