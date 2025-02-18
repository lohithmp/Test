C:\>taskkill /IM java.exe /F
ERROR: The process "java.exe" not found.

C:\>rd /s /q C:\tmp\kraft-combined-logs
The system cannot find the file specified.

C:\>cd kafka_2.13-3.8.1

C:\kafka_2.13-3.8.1>bin\windows\kafka-server-start.bat config\kraft\server.properties
[2025-02-18 13:23:21,975] INFO Registered kafka:type=kafka.Log4jController MBean (kafka.utils.Log4jControllerRegistration$)
[2025-02-18 13:23:22,254] INFO Setting -D jdk.tls.rejectClientInitiatedRenegotiation=true to disable client-initiated TLS renegotiation (org.apache.zookeeper.common.X509Util)
[2025-02-18 13:23:22,258] INFO RemoteLogManagerConfig values:
        log.local.retention.bytes = -2
        log.local.retention.ms = -2
        remote.fetch.max.wait.ms = 500
        remote.log.index.file.cache.total.size.bytes = 1073741824
        remote.log.manager.copier.thread.pool.size = 10
        remote.log.manager.copy.max.bytes.per.second = 9223372036854775807
        remote.log.manager.copy.quota.window.num = 11
        remote.log.manager.copy.quota.window.size.seconds = 1
        remote.log.manager.expiration.thread.pool.size = 10
        remote.log.manager.fetch.max.bytes.per.second = 9223372036854775807
        remote.log.manager.fetch.quota.window.num = 11
        remote.log.manager.fetch.quota.window.size.seconds = 1
        remote.log.manager.task.interval.ms = 30000
        remote.log.manager.task.retry.backoff.max.ms = 30000
        remote.log.manager.task.retry.backoff.ms = 500
        remote.log.manager.task.retry.jitter = 0.2
        remote.log.manager.thread.pool.size = 10
        remote.log.metadata.custom.metadata.max.bytes = 128
        remote.log.metadata.manager.class.name = org.apache.kafka.server.log.remote.metadata.storage.TopicBasedRemoteLogMetadataManager
        remote.log.metadata.manager.class.path = null
        remote.log.metadata.manager.impl.prefix = rlmm.config.
        remote.log.metadata.manager.listener.name = null
        remote.log.reader.max.pending.tasks = 100
        remote.log.reader.threads = 10
        remote.log.storage.manager.class.name = null
        remote.log.storage.manager.class.path = null
        remote.log.storage.manager.impl.prefix = rsm.config.
        remote.log.storage.system.enable = false
 (org.apache.kafka.server.log.remote.storage.RemoteLogManagerConfig)
[2025-02-18 13:23:22,391] ERROR Exiting Kafka due to fatal exception (kafka.Kafka$)
java.lang.RuntimeException: No readable meta.properties files found.
        at org.apache.kafka.metadata.properties.MetaPropertiesEnsemble.verify(MetaPropertiesEnsemble.java:486)
        at kafka.server.KafkaRaftServer$.initializeLogDirs(KafkaRaftServer.scala:142)
        at kafka.server.KafkaRaftServer.<init>(KafkaRaftServer.scala:60)
        at kafka.Kafka$.buildServer(Kafka.scala:82)
        at kafka.Kafka$.main(Kafka.scala:90)
        at kafka.Kafka.main(Kafka.scala)
