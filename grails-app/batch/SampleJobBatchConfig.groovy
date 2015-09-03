import com.bonial.batch.jobs.SampleJobTasklet

/**
 * batch-processor
 * @author Konstantin Bork
 * @version 1.0
 * @created 08/28/2015
 *
 * An example for the look of Spring Batch job configurations.
 */

beans {

    /*
        Necessary line for BatchConfig files.
     */
    xmlns batch:"http://www.springframework.org/schema/batch"

    /**
        A Spring Batch job consists of at least one step. Each step is performed one after the other.
        Each job and each step have an ID to identify them so prevent having jobs and steps with the same name.
        Each step can then look in one of two ways:
            1) Data are read from a source, processed and written persistently into a file, database etc.
            2) One tasklet executes a task.
        The tasklet is referenced in the step.
     */
    batch.job(id: 'sampleJob') {
        batch.step(id: 'printStep') {
            batch.tasklet(ref: 'printHelloWorld')
        }
    }

    /**
        Reference of the tasklet. It gets the Tasklet implementation as a parameter and sets a bean.
     */
    printHelloWorld(SampleJobTasklet) { bean ->
        bean.autowire = 'byName'
    }

}