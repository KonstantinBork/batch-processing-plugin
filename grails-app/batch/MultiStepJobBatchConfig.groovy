import com.bonial.batch.jobs.PrintMessageTasklet

beans {

    xmlns batch:"http://www.springframework.org/schema/batch"

    batch.job(id: 'multiStepJob') {
        batch.step(id: 'step1', next: 'step2') {
            batch.tasklet(ref: 'printStart')
        }
        batch.step(id: 'step2', next: 'step3') {
            batch.tasklet(ref: 'printMessage')
        }
        batch.step(id: 'step3', next: 'step4') {
            batch.tasklet(ref: 'printMessage')
        }
        batch.step(id: 'step4', next: 'step5') {
            batch.tasklet(ref: 'printMessage')
        }
        batch.step(id: 'step5', next: 'step6') {
            batch.tasklet(ref: 'printMessage')
        }
        batch.step(id: 'step6', next: 'step7') {
            batch.tasklet(ref: 'printMessage')
        }
        batch.step(id: 'step7') {
            batch.tasklet(ref: 'printEnd')
        }
    }

    printStart(PrintMessageTasklet) { bean ->
        bean.autowire = "byName"
        msg = "Starting job!"
    }

    printMessage(PrintMessageTasklet) { bean ->
        bean.autowire = "byName"
        msg = "Running step"
    }

    printEnd(PrintMessageTasklet) { bean ->
        bean.autowire = "byName"
        msg = "Finishing job!"
    }

}