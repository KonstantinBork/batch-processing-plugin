package com.bonial.batch

import com.bonial.batch.interfaces.Consumer
import com.bonial.batch.interfaces.Worker
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobParameter
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
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

    def priorityBatchQueueService

    static final int MAX_WORKERS = 15                                               // number of workers
    ConcurrentLinkedQueue<Worker> availableWorkers = new ConcurrentLinkedQueue<>()  // list with all available workers
    ConcurrentLinkedQueue<Worker> busyWorkers = new ConcurrentLinkedQueue<>()       // list with all busy workers

    BatchConsumerService() {
        for(int i = 0; i < MAX_WORKERS; i++)
            availableWorkers.add(new SimpleWorker())
    }

    @Override
    void consumeNextTask() {
        Message m = priorityBatchQueueService.dequeue()
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
    }

}