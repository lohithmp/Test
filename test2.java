2025-03-01T18:21:07.734+05:30  INFO 8948 --- [epay_Gateway_Offline_Process_Service] [           main] c.e.g.GatewayPoolingServiceApplication   : Started GatewayPoolingServiceApplication in 15.297 seconds (process running for 18.219)
2025-03-01T18:21:08.176+05:30 ERROR 8948 --- [epay_Gateway_Offline_Process_Service] [   scheduling-1] o.s.s.s.TaskUtils$LoggingErrorHandler    : Unexpected error occurred in scheduled task

org.springframework.jdbc.BadSqlGrammarException: PreparedStatementCallback; bad SQL grammar [INSERT INTO BATCH_JOB_EXECUTION_PARAMS(JOB_EXECUTION_ID, PARAMETER_NAME, PARAMETER_TYPE, PARAMETER_VALUE, IDENTIFYING)
	VALUES (?, ?, ?, ?, ?)
]
	at org.springframework.jdbc.support.SQLStateSQLExceptionTranslator.doTranslate(SQLStateSQLExceptionTranslator.java:112) ~[spring-jdbc-6.1.16.jar:6.1.16]
	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:107) ~[spring-jdbc-6.1.16.jar:6.1.16]
	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:116) ~[spring-jdbc-6.1.16.jar:6.1.16]
	at org.springframework.jdbc.core.JdbcTemplate.translateException(JdbcTemplate.java:1548) ~[spring-jdbc-6.1.16.jar:6.1.16]
	at org.springframework.jdbc.core.JdbcTemplate.execute(JdbcTemplate.java:677) ~[spring-jdbc-6.1.16.jar:6.1.16]
	at org.springframework.jdbc.core.JdbcTemplate.execute(JdbcTemplate.java:701) ~[spring-jdbc-6.1.16.jar:6.1.16]
	at org.springframework.jdbc.core.JdbcTemplate.batchUpdate(JdbcTemplate.java:1101) ~[spring-jdbc-6.1.16.jar:6.1.16]
	at org.springframework.batch.core.repository.dao.JdbcJobExecutionDao.insertJobParameters(JdbcJobExecutionDao.java:431) ~[spring-batch-core-5.1.3.jar:5.1.3]
	at org.springframework.batch.core.repository.dao.JdbcJobExecutionDao.saveJobExecution(JdbcJobExecutionDao.java:251) ~[spring-batch-core-5.1.3.jar:5.1.3]
	at org.springframework.batch.core.repository.support.SimpleJobRepository.createJobExecution(SimpleJobRepository.java:178) ~[spring-batch-core-5.1.3.jar:5.1.3]
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:580) ~[na:na]
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:355) ~[spring-aop-6.1.16.jar:6.1.16]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:196) ~[spring-aop-6.1.16.jar:6.1.16]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163) ~[spring-aop-6.1.16.jar:6.1.16]
	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:379) ~[spring-tx-6.1.16.jar:6.1.16]
	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:119) ~[spring-tx-6.1.16.jar:6.1.16]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.1.16.jar:6.1.16]
	at org.springframework.batch.core.repository.support.AbstractJobRepositoryFactoryBean.lambda$getObject$0(AbstractJobRepositoryFactoryBean.java:204) ~[spring-batch-core-5.1.3.jar:5.1.3]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.1.16.jar:6.1.16]
	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:223) ~[spring-aop-6.1.16.jar:6.1.16]
	at jdk.proxy2/jdk.proxy2.$Proxy146.createJobExecution(Unknown Source) ~[na:na]
	at org.springframework.batch.core.launch.support.SimpleJobLauncher.run(SimpleJobLauncher.java:145) ~[spring-batch-core-5.1.3.jar:5.1.3]
	at org.springframework.batch.core.launch.support.TaskExecutorJobLauncher.run(TaskExecutorJobLauncher.java:59) ~[spring-batch-core-5.1.3.jar:5.1.3]
	at com.epay.gateway.schedular.GatewayPoolingScheduler.runBatchJob(GatewayPoolingScheduler.java:24) ~[main/:na]
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:580) ~[na:na]
	at org.springframework.scheduling.support.ScheduledMethodRunnable.runInternal(ScheduledMethodRunnable.java:130) ~[spring-context-6.1.16.jar:6.1.16]
	at org.springframework.scheduling.support.ScheduledMethodRunnable.lambda$run$2(ScheduledMethodRunnable.java:124) ~[spring-context-6.1.16.jar:6.1.16]
	at io.micrometer.observation.Observation.observe(Observation.java:499) ~[micrometer-observation-1.13.10.jar:1.13.10]
	at org.springframework.scheduling.support.ScheduledMethodRunnable.run(ScheduledMethodRunnable.java:124) ~[spring-context-6.1.16.jar:6.1.16]
	at org.springframework.scheduling.support.DelegatingErrorHandlingRunnable.run(DelegatingErrorHandlingRunnable.java:54) ~[spring-context-6.1.16.jar:6.1.16]
	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:572) ~[na:na]
	at java.base/java.util.concurrent.FutureTask.runAndReset(FutureTask.java:358) ~[na:na]
	at java.base/java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:305) ~[na:na]
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144) ~[na:na]
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642) ~[na:na]
	at java.base/java.lang.Thread.run(Thread.java:1583) ~[na:na]
Caused by: java.sql.BatchUpdateException: ORA-00904: "PARAMETER_VALUE": invalid identifier

https://docs.oracle.com/error-help/db/ora-00904/
	at oracle.jdbc.driver.OraclePreparedStatement.generateBatchUpdateException(OraclePreparedStatement.java:11377) ~[ojdbc11-23.5.0.24.07.jar:23.5.0.24.07]
	at oracle.jdbc.driver.OraclePreparedStatement.executeBatchWithoutQueue(OraclePreparedStatement.java:10703) ~[ojdbc11-23.5.0.24.07.jar:23.5.0.24.07]
	at oracle.jdbc.driver.OraclePreparedStatement.executeLargeBatch(OraclePreparedStatement.java:10407) ~[ojdbc11-23.5.0.24.07.jar:23.5.0.24.07]
	at oracle.jdbc.driver.OracleStatement.executeBatch(OracleStatement.java:5658) ~[ojdbc11-23.5.0.24.07.jar:23.5.0.24.07]
	at oracle.jdbc.driver.OracleStatementWrapper.executeBatch(OracleStatementWrapper.java:291) ~[ojdbc11-23.5.0.24.07.jar:23.5.0.24.07]
	at com.zaxxer.hikari.pool.ProxyStatement.executeBatch(ProxyStatement.java:127) ~[HikariCP-5.1.0.jar:na]
	at com.zaxxer.hikari.pool.HikariProxyPreparedStatement.executeBatch(HikariProxyPreparedStatement.java) ~[HikariCP-5.1.0.jar:na]
	at org.springframework.jdbc.core.JdbcTemplate.lambda$batchUpdate$4(JdbcTemplate.java:1117) ~[spring-jdbc-6.1.16.jar:6.1.16]
	at org.springframework.jdbc.core.JdbcTemplate.execute(JdbcTemplate.java:658) ~[spring-jdbc-6.1.16.jar:6.1.16]
	... 33 common frames omitted

Caused by: java.sql.BatchUpdateException: ORA-00904: "PARAMETER_VALUE": invalid identifier
