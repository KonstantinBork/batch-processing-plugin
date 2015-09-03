import com.bonial.batch.jobs.LongJobTasklet

/**
 * batch-processor
 * @author Konstantin Bork
 * @version 1.0
 * @created 09/01/2015
 *
 * An example for the look of Spring Batch job configurations.
 */

beans {

    xmlns batch:"http://www.springframework.org/schema/batch"

    batch.job(id: 'longJob') {
        batch.step(id: 'longStep') {
            batch.tasklet(ref: 'longMethod')
        }
    }

    longMethod(LongJobTasklet) { bean ->
        bean.autowire = 'byName'
    }

}