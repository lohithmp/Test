java -jar spring-cloud-dataflow-server-2.11.5.jar --spring.datasource.driver-class-name=oracle.jdbc.OracleDriver --spring.datasource.url=jdbc:oracle:thin:@10.177.134.124:1590:epaydbdev1 --spring.datasource.username=NIRMAL_GURJAR --spring.datasource.password=NIRMAL_GURJAR --spring.jpa.show-sql=true
2025-02-18 17:29:09.118  INFO 560 --- [           main] o.s.c.d.a.l.ProfileApplicationListener   : Setting property 'spring.cloud.kubernetes.enabled' to false.
2025-02-18 17:29:09.392  INFO 560 --- [kground-preinit] o.h.validator.internal.util.Version      : HV000001: Hibernate Validator 6.2.5.Final
2025-02-18 17:29:09.597  INFO 560 --- [           main] s.c.d.c.p.t.DatabaseTypeAwareInitializer : checking database driver type:oracle.jdbc.OracleDriver
2025-02-18 17:29:09.723  WARN 560 --- [           main] ubernetesProfileEnvironmentPostProcessor : Not running inside kubernetes. Skipping 'kubernetes' profile activation.
2025-02-18 17:29:10.876  INFO 560 --- [           main] o.s.c.d.a.l.ProfileApplicationListener   : Setting property 'spring.cloud.kubernetes.enabled' to false.
  ____                              ____ _                __
 / ___| _ __  _ __(_)_ __   __ _   / ___| | ___  _   _  __| |
 \___ \| '_ \| '__| | '_ \ / _` | | |   | |/ _ \| | | |/ _` |
  ___) | |_) | |  | | | | | (_| | | |___| | (_) | |_| | (_| |
 |____/| .__/|_|  |_|_| |_|\__, |  \____|_|\___/ \__,_|\__,_|
  ____ |_|    _          __|___/                 __________
 |  _ \  __ _| |_ __ _  |  ___| | _____      __  \ \ \ \ \ \
 | | | |/ _` | __/ _` | | |_  | |/ _ \ \ /\ / /   \ \ \ \ \ \
 | |_| | (_| | || (_| | |  _| | | (_) \ V  V /    / / / / / /
 |____/ \__,_|\__\__,_| |_|   |_|\___/ \_/\_/    /_/_/_/_/_/



2025-02-18 17:29:11.128  INFO 560 --- [           main] s.c.d.c.p.t.DatabaseTypeAwareInitializer : checking database driver type:oracle.jdbc.OracleDriver
2025-02-18 17:29:11.239  INFO 560 --- [           main] c.c.c.ConfigServicePropertySourceLocator : Fetching config from server at : http://localhost:8888
2025-02-18 17:29:11.474  INFO 560 --- [           main] c.c.c.ConfigServicePropertySourceLocator : Connect Timeout Exception on Url - http://localhost:8888. Will be trying the next url if available
2025-02-18 17:29:11.475  WARN 560 --- [           main] c.c.c.ConfigServicePropertySourceLocator : Could not locate PropertySource: I/O error on GET request for "http://localhost:8888/spring-cloud-dataflow-server/local": Connection refused: getsockopt; nested exception is java.net.ConnectException: Connection refused: getsockopt
2025-02-18 17:29:11.480  WARN 560 --- [           main] ubernetesProfileEnvironmentPostProcessor : Not running inside kubernetes. Skipping 'kubernetes' profile activation.
2025-02-18 17:29:11.482  INFO 560 --- [           main] o.s.c.d.s.s.DataFlowServerApplication    : The following 1 profile is active: "local"
2025-02-18 17:29:13.388  INFO 560 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Multiple Spring Data modules found, entering strict repository configuration mode
2025-02-18 17:29:13.389  INFO 560 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data Map repositories in DEFAULT mode.
2025-02-18 17:29:13.685  INFO 560 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 255 ms. Found 1 Map repository interfaces.
2025-02-18 17:29:14.470  INFO 560 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Multiple Spring Data modules found, entering strict repository configuration mode
2025-02-18 17:29:14.474  INFO 560 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2025-02-18 17:29:14.620  INFO 560 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 10 ms. Found 0 JPA repository interfaces.
2025-02-18 17:29:15.272  INFO 560 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Multiple Spring Data modules found, entering strict repository configuration mode
2025-02-18 17:29:15.273  INFO 560 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2025-02-18 17:29:15.461  INFO 560 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 187 ms. Found 5 JPA repository interfaces.
2025-02-18 17:29:15.974  INFO 560 --- [           main] o.s.cloud.context.scope.GenericScope     : BeanFactory id=4642363d-fae0-307b-8d73-dc59fe3ee63a
2025-02-18 17:29:17.241  INFO 560 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 9091 (http)
2025-02-18 17:29:17.256  INFO 560 --- [           main] o.a.coyote.http11.Http11NioProtocol      : Initializing ProtocolHandler ["http-nio-9091"]
2025-02-18 17:29:17.378  INFO 560 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2025-02-18 17:29:17.385  INFO 560 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.93]
2025-02-18 17:29:17.645  INFO 560 --- [           main] o.a.c.c.C.[.[.[/transaction/v1]          : Initializing Spring embedded WebApplicationContext
2025-02-18 17:29:17.871 ERROR 560 --- [           main] o.s.b.web.embedded.tomcat.TomcatStarter  : Error starting Tomcat context. Exception: org.springframework.beans.factory.UnsatisfiedDependencyException. Message: Error creating bean with name 'webMvcMetricsFilter' defined 
in class path resource [org/springframework/boot/actuate/autoconfigure/metrics/web/servlet/WebMvcMetricsAutoConfiguration.class]: Unsatisfied dependency expressed through method 'webMvcMetricsFilter' parameter 0; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'simpleMeterRegistry' defined in class path resource [org/springframework/boot/actuate/autoconfigure/metrics/export/simple/SimpleMetricsExportAutoConfiguration.class]: Initialization of bean failed; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'dataSourcePoolMetadataMeterBinder' defined in class path resource [org/springframework/boot/actuate/autoconfigure/metrics/jdbc/DataSourcePoolMetricsAutoConfiguration$DataSourcePoolMetadataMetricsConfiguration.class]: Unsatisfied dependency expressed through method 'dataSourcePoolMetadataMeterBinder' parameter 0; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'dataSource' defined in class path resource [org/springframework/boot/autoconfigure/jdbc/DataSourceConfiguration$Hikari.class]: Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [com.zaxxer.hikari.HikariDataSource]: Factory method 
'dataSource' threw exception; nested exception is java.lang.IllegalStateException: Cannot load driver class: oracle.jdbc.OracleDriver     
2025-02-18 17:29:18.049  INFO 560 --- [           main] o.apache.catalina.core.StandardService   : Stopping service [Tomcat]
2025-02-18 17:29:18.172  WARN 560 --- [           main] ConfigServletWebServerApplicationContext : Exception encountered during context initialization - cancelling refresh attempt: org.springframework.context.ApplicationContextException: Unable to start web server; nested exception is org.springframework.boot.web.server.WebServerException: Unable to start embedded Tomcat
2025-02-18 17:29:18.342 ERROR 560 --- [           main] o.s.boot.SpringApplication               : Application run failed

org.springframework.context.ApplicationContextException: Unable to start web server; nested exception is org.springframework.boot.web.server.WebServerException: Unable to start embedded Tomcat
        at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.onRefresh(ServletWebServerApplicationContext.java:165)
        at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:585)
        at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:147)
        at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:732)
        at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:409)
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:308)
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:1300)
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:1289)
        at org.springframework.cloud.dataflow.server.single.DataFlowServerApplication.main(DataFlowServerApplication.java:54)
        at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
        at java.base/java.lang.reflect.Method.invoke(Method.java:580)
        at org.springframework.boot.loader.MainMethodRunner.run(MainMethodRunner.java:49)
        at org.springframework.boot.loader.Launcher.launch(Launcher.java:108)
        at org.springframework.boot.loader.Launcher.launch(Launcher.java:58)
        at org.springframework.boot.loader.JarLauncher.main(JarLauncher.java:65)
Caused by: org.springframework.boot.web.server.WebServerException: Unable to start embedded Tomcat
        at org.springframework.boot.web.embedded.tomcat.TomcatWebServer.initialize(TomcatWebServer.java:142)
        at org.springframework.boot.web.embedded.tomcat.TomcatWebServer.<init>(TomcatWebServer.java:104)
        at org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory.getTomcatWebServer(TomcatServletWebServerFactory.java:481)
        at org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory.getWebServer(TomcatServletWebServerFactory.java:211)        at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.createWebServer(ServletWebServerApplicationContext.java:184)
        at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.onRefresh(ServletWebServerApplicationContext.java:162)
        ... 14 common frames omitted
Caused by: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'webMvcMetricsFilter' defined in class path resource [org/springframework/boot/actuate/autoconfigure/metrics/web/servlet/WebMvcMetricsAutoConfiguration.class]: Unsatisfied dependency expressed through method 'webMvcMetricsFilter' parameter 0; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'simpleMeterRegistry' defined in class path resource [org/springframework/boot/actuate/autoconfigure/metrics/export/simple/SimpleMetricsExportAutoConfiguration.class]: Initialization of bean failed; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'dataSourcePoolMetadataMeterBinder' defined in class path resource [org/springframework/boot/actuate/autoconfigure/metrics/jdbc/DataSourcePoolMetricsAutoConfiguration$DataSourcePoolMetadataMetricsConfiguration.class]: Unsatisfied dependency expressed through method 'dataSourcePoolMetadataMeterBinder' parameter 0; nested exception 
is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'dataSource' defined in class path resource [org/springframework/boot/autoconfigure/jdbc/DataSourceConfiguration$Hikari.class]: Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [com.zaxxer.hikari.HikariDataSource]: Factory method 'dataSource' threw exception; nested exception is java.lang.IllegalStateException: Cannot load driver class: oracle.jdbc.OracleDriver      
        at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:794)
        at org.springframework.beans.factory.support.ConstructorResolver.instantiateUsingFactoryMethod(ConstructorResolver.java:532)      
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.instantiateUsingFactoryMethod(AbstractAutowireCapableBeanFactory.java:1352)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1195)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:336)
        at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)     
        at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:334)
        at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:214)
        at org.springframework.boot.web.servlet.ServletContextInitializerBeans.getOrderedBeansOfType(ServletContextInitializerBeans.java:213)
        at org.springframework.boot.web.servlet.ServletContextInitializerBeans.getOrderedBeansOfType(ServletContextInitializerBeans.java:204)
        at org.springframework.boot.web.servlet.ServletContextInitializerBeans.addServletContextInitializerBeans(ServletContextInitializerBeans.java:98)
        at org.springframework.boot.web.servlet.ServletContextInitializerBeans.<init>(ServletContextInitializerBeans.java:86)
        at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.getServletContextInitializerBeans(ServletWebServerApplicationContext.java:262)
        at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.selfInitialize(ServletWebServerApplicationContext.java:236)
        at org.springframework.boot.web.embedded.tomcat.TomcatStarter.onStartup(TomcatStarter.java:53)
        at org.apache.catalina.core.StandardContext.startInternal(StandardContext.java:4438)
        at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:164)
        at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1203)
        at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1193)
        at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:317)
        at org.apache.tomcat.util.threads.InlineExecutorService.execute(InlineExecutorService.java:75)
        at java.base/java.util.concurrent.AbstractExecutorService.submit(AbstractExecutorService.java:145)
        at org.apache.catalina.core.ContainerBase.startInternal(ContainerBase.java:749)
        at org.apache.catalina.core.StandardHost.startInternal(StandardHost.java:721)
        at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:164)
        at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1203)
        at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1193)
        at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:317)
        at org.apache.tomcat.util.threads.InlineExecutorService.execute(InlineExecutorService.java:75)
        at java.base/java.util.concurrent.AbstractExecutorService.submit(AbstractExecutorService.java:145)
        at org.apache.catalina.core.ContainerBase.startInternal(ContainerBase.java:749)
        at org.apache.catalina.core.StandardEngine.startInternal(StandardEngine.java:211)
        at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:164)
        at org.apache.catalina.core.StandardService.startInternal(StandardService.java:415)
        at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:164)
        at org.apache.catalina.core.StandardServer.startInternal(StandardServer.java:878)
        at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:164)
        at org.apache.catalina.startup.Tomcat.start(Tomcat.java:438)
        at org.springframework.boot.web.embedded.tomcat.TomcatWebServer.initialize(TomcatWebServer.java:123)
        ... 19 common frames omitted
Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'simpleMeterRegistry' defined in class path resource [org/springframework/boot/actuate/autoconfigure/metrics/export/simple/SimpleMetricsExportAutoConfiguration.class]: Initialization of bean failed; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'dataSourcePoolMetadataMeterBinder' defined in class path resource [org/springframework/boot/actuate/autoconfigure/metrics/jdbc/DataSourcePoolMetricsAutoConfiguration$DataSourcePoolMetadataMetricsConfiguration.class]: Unsatisfied dependency expressed through method 'dataSourcePoolMetadataMeterBinder' parameter 0; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'dataSource' defined in class path resource [org/springframework/boot/autoconfigure/jdbc/DataSourceConfiguration$Hikari.class]: Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [com.zaxxer.hikari.HikariDataSource]: Factory method 'dataSource' threw exception; nested exception is java.lang.IllegalStateException: Cannot load driver class: oracle.jdbc.OracleDriver
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:628)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:336)
        at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)     
        at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:334)
        at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:209)
        at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1391) 
        at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1311)   
        at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:904)
        at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:781)
        ... 59 common frames omitted
Caused by: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'dataSourcePoolMetadataMeterBinder' defined in class path resource [org/springframework/boot/actuate/autoconfigure/metrics/jdbc/DataSourcePoolMetricsAutoConfiguration$DataSourcePoolMetadataMetricsConfiguration.class]: Unsatisfied dependency expressed through method 'dataSourcePoolMetadataMeterBinder' parameter 0; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'dataSource' defined in class path resource [org/springframework/boot/autoconfigure/jdbc/DataSourceConfiguration$Hikari.class]: Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [com.zaxxer.hikari.HikariDataSource]: Factory method 'dataSource' threw exception; nested exception is java.lang.IllegalStateException: Cannot load driver class: oracle.jdbc.OracleDriver
        at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:794)
        at org.springframework.beans.factory.support.ConstructorResolver.instantiateUsingFactoryMethod(ConstructorResolver.java:532)      
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.instantiateUsingFactoryMethod(AbstractAutowireCapableBeanFactory.java:1352)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1195)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:336)
        at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)     
        at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:334)
        at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:209)
        at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        at org.springframework.beans.factory.support.DefaultListableBeanFactory.addCandidateEntry(DefaultListableBeanFactory.java:1616)   
        at org.springframework.beans.factory.support.DefaultListableBeanFactory.findAutowireCandidates(DefaultListableBeanFactory.java:1573)
        at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveMultipleBeans(DefaultListableBeanFactory.java:1417)        at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1349) 
        at org.springframework.beans.factory.support.DefaultListableBeanFactory$DependencyObjectProvider.resolveStream(DefaultListableBeanFactory.java:2119)
        at org.springframework.beans.factory.support.DefaultListableBeanFactory$DependencyObjectProvider.orderedStream(DefaultListableBeanFactory.java:2113)
        at org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryConfigurer.addBinders(MeterRegistryConfigurer.java:87)     
        at org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryConfigurer.configure(MeterRegistryConfigurer.java:68)      
        at org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryPostProcessor.postProcessAfterInitialization(MeterRegistryPostProcessor.java:64)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization(AbstractAutowireCapableBeanFactory.java:455)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1808)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        ... 69 common frames omitted
Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'dataSource' defined in class path resource [org/springframework/boot/autoconfigure/jdbc/DataSourceConfiguration$Hikari.class]: Bean instantiation via factory method failed; nestmethod 'dataSource' threw exception; nested exception is java.lang.IllegalStateException: Cannot load driver class: oracle.jdbc.OracleDriver
        at org.springframework.beans.factory.support.ConstructorResolver.instantiate(ConstructorResolver.java:646)
        at org.springframework.beans.factory.support.ConstructorResolver.instantiateUsingFactoryMethod(ConstructorResolver.java:626)      
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.instantiateUsingFactoryMethod(AbstractAutowireCapableBeanFactory.java:1352)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1195)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:336)
        at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)     
        at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:334)
        at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:209)
        at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        at org.springframework.beans.factory.support.DefaultListableBeanFactory.addCandidateEntry(DefaultListableBeanFactory.java:1609)   
        at org.springframework.beans.factory.support.DefaultListableBeanFactory.findAutowireCandidates(DefaultListableBeanFactory.java:1573)
        at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveMultipleBeans(DefaultListableBeanFactory.java:1492)        at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1349) 
        at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1311)   
        at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:904)
        at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:781)
        ... 91 common frames omitted
Caused by: org.springframework.beans.BeanInstantiationException: Failed to instantiate [com.zaxxer.hikari.HikariDataSource]: Factory method 'dataSource' threw exception; nested exception is java.lang.IllegalStateException: Cannot load driver class: oracle.jdbc.OracleDriver   
        at org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:185)        
        at org.springframework.beans.factory.support.ConstructorResolver.instantiate(ConstructorResolver.java:641)
        ... 108 common frames omitted
Caused by: java.lang.IllegalStateException: Cannot load driver class: oracle.jdbc.OracleDriver
        at org.springframework.util.Assert.state(Assert.java:97)
        at org.springframework.boot.autoconfigure.jdbc.DataSourceProperties.determineDriverClassName(DataSourceProperties.java:175)       
        at org.springframework.boot.autoconfigure.jdbc.DataSourceProperties.initializeDataSourceBuilder(DataSourceProperties.java:125)    
        at org.springframework.boot.autoconfigure.jdbc.DataSourceConfiguration.createDataSource(DataSourceConfiguration.java:48)
        at org.springframework.boot.autoconfigure.jdbc.DataSourceConfiguration$Hikari.dataSource(DataSourceConfiguration.java:90)
        at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
        at java.base/java.lang.reflect.Method.invoke(Method.java:580)
        at org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:154)        
        ... 109 common frames omitted
