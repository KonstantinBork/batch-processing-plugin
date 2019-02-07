// configuration for plugin testing - will not be included in the plugin zip

log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    warn   'org.mortbay.log'
}
plugin {
       springBatch {
              jmx {
                     enable = true
                     remote {
                            enable = false
                     }
              }
              loadTables = true
              database = "h2"
       }
}
grails.plugin.databasemigration.ignoredObjects = ['BATCH_JOB_EXECUTION',
                                                  'BATCH_JOB_EXECUTION_CONTEXT','BATCH_JOB_EXECUTION_SEQ',
                                                  'BATCH_JOB_INSTANCE','BATCH_JOB_PARAMS','BATCH_JOB_SEQ',
                                                  'BATCH_STEP_EXECUTION', 'BATCH_STEP_EXECUTION_CONTEXT',
                                                  'BATCH_STEP_EXECUTION_SEQ',
                                                  'IDX_BATCH_JOB_INSTANCE_JOB_NAME_JOB_KEY','JOB_INST_UN',
                                                  'IDX_BATCH_STEP_EXECUTION_STEP_NAME_JOB_EXECUTION_ID',
                                                  'IDX_BATCH_STEP_EXECUTION_VERSION']
// Uncomment and edit the following lines to start using Grails encoding & escaping improvements

/* remove this line 
// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside null
                scriptlet = 'none' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        filteringCodecForContentType {
            //'text/html' = 'html'
        }
    }
}
remove this line */
