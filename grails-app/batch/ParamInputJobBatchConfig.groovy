import com.bonial.batch.jobs.FileLineMapper
import com.bonial.batch.jobs.NameItemProcessor
import com.bonial.batch.jobs.NameItemWriter
import org.springframework.batch.item.file.FlatFileItemReader

beans {

    xmlns batch:"http://www.springframework.org/schema/batch"

    batch.job(id: 'paramInputJob') {
        batch.step(id: 'paramInput') {
            batch.tasklet {
                batch.chunk (
                        reader: 'fileReader',
                        processor: 'lineProcessor',
                        writer: 'fileWriter',
                        'commit-interval': 5
                )
            }
        }
    }

    fileReader(FlatFileItemReader) { bean ->
        bean.scope = 'step'
        resource = "#{jobParameters['file']}"
        lineMapper = ref('fileLineMapper')
    }

    fileLineMapper(FileLineMapper)

    lineProcessor(NameItemProcessor) { bean ->
        bean.autowire = "byName"
    }

    fileWriter(NameItemWriter) { bean ->
        bean.autowire = "byName"
    }

}