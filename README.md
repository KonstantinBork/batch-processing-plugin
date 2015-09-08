# Batch Processing Plugin for Grails (beta)

The Batch Processing Plugin allows your application to use batch jobs to process tasks. It uses Spring Batch to define
batch jobs and Spring Integration to save created jobs in a queue.

### Requirements
Grails 2.2 or above

### Usage
#### Installation
If you want to use this plugin in your Grails application you just have to include this plugin into your application
via your BuildConfig.groovy file. Then add following code snippet to your `Config.groovy`:

	pugin {
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
                            	                      
Then add the following code snippet to your `DataSource.groovy`:

	environments {
        development {
            dataSource {
                dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
                url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=TRUE"
            }
        }
        test {
            dataSource {
                dbCreate = "create-drop"
                url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=TRUE"
            }
        }
        mysql {
            dataSource {
                driverClassName = "com.mysql.jdbc.Driver"
                dbCreate = "create-drop"
                def jdbcUrl = "jdbc:mysql://localhost/springbatch?autoReconnect=true"
                url = jdbcUrl
                username = ""
                password = ""
            }
        }
        production {
            dataSource {
                dbCreate = "update"
                url = "jdbc:h2:prodDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
                properties {
                    // See http://grails.org/doc/latest/guide/conf.html#dataSource for documentation
                    jmxEnabled = true
                    initialSize = 5
                    maxActive = 50
                    minIdle = 5
                    maxIdle = 25
                    maxWait = 10000
                    maxAge = 10 * 60000
                    timeBetweenEvictionRunsMillis = 5000
                    minEvictableIdleTimeMillis = 60000
                    validationQuery = "SELECT 1"
                    validationQueryTimeout = 3
                    validationInterval = 15000
                    testOnBorrow = true
                    testWhileIdle = true
                    testOnReturn = false
                    jdbcInterceptors = "ConnectionState"
                    defaultTransactionIsolation = java.sql.Connection.TRANSACTION_READ_COMMITTED
                }
            }
        }
    }

Of course, you are able to change both files to your needs. These snippets are minimal requirements to run your application
with this plugin.
    
                     
#### Batch Job Creation
When the plugin is installed successfully you should see a folder called `batch` in your `grails-app` directory. You define
your jobs here with Groovy DSL. It is important that the file names end with `BatchConfig`. Keep an eye on all the job and
step ids, too, as they should be unique. To prevent problems, find unique job names and name your steps like the scheme
`[jobName]_[stepName]`. After defining your jobs, you implement your steps in your application's `src` folder.  
You find job examples in the plugin source code.
                     
### Dependencies
* Spring Batch Plugin 2.0.0, more information [here](https://github.com/johnrengelman/grails-spring-batch)
* Spring Integration 2.2.6.RELEASE, more information [here](http://docs.spring.io/spring-integration/docs/2.2.6.RELEASE/reference/html/)

### Further Information
Version 0.8 build 27  
E-mail: konstantin.bork[at]gmail.com  
Website: [https://github.com/KonstantinBork/batch-processing-plugin](https://github.com/KonstantinBork/batch-processing-plugin)  
Twitter: [https://twitter.com/flakenerd](https://twitter.com/flakenerd)  

&copy; 2015 Konstantin Bork  
Licensed under the Apache License, Version 2.0 (see LICENSE).