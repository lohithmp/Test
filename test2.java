http://localhost:8083/connectors/admin-service-connector/status

{
    "name": "admin-service-connector",
    "connector": {
        "state": "RUNNING",
        "worker_id": "10.30.64.173:8083"
    },
    "tasks": [
        {
            "id": 0,
            "state": "FAILED",
            "worker_id": "10.30.64.173:8083",
            "trace": "org.apache.kafka.connect.errors.ConnectException: Error configuring an instance of KafkaSchemaHistory; check the logs for details\r\n\tat io.debezium.storage.kafka.history.KafkaSchemaHistory.configure(KafkaSchemaHistory.java:208)\r\n\tat io.debezium.relational.HistorizedRelationalDatabaseConnectorConfig.getSchemaHistory(HistorizedRelationalDatabaseConnectorConfig.java:137)\r\n\tat io.debezium.relational.HistorizedRelationalDatabaseSchema.<init>(HistorizedRelationalDatabaseSchema.java:50)\r\n\tat io.debezium.connector.oracle.OracleDatabaseSchema.<init>(OracleDatabaseSchema.java:59)\r\n\tat io.debezium.connector.oracle.OracleConnectorTask.start(OracleConnectorTask.java:77)\r\n\tat io.debezium.connector.common.BaseSourceTask.start(BaseSourceTask.java:253)\r\n\tat org.apache.kafka.connect.runtime.AbstractWorkerSourceTask.initializeAndStart(AbstractWorkerSourceTask.java:278)\r\n\tat org.apache.kafka.connect.runtime.WorkerTask.doStart(WorkerTask.java:175)\r\n\tat org.apache.kafka.connect.runtime.WorkerTask.doRun(WorkerTask.java:224)\r\n\tat org.apache.kafka.connect.runtime.WorkerTask.run(WorkerTask.java:280)\r\n\tat org.apache.kafka.connect.runtime.AbstractWorkerSourceTask.run(AbstractWorkerSourceTask.java:78)\r\n\tat org.apache.kafka.connect.runtime.isolation.Plugins.lambda$withClassLoader$1(Plugins.java:237)\r\n\tat java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:572)\r\n\tat java.base/java.util.concurrent.FutureTask.run(FutureTask.java:317)\r\n\tat java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144)\r\n\tat java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642)\r\n\tat java.base/java.lang.Thread.run(Thread.java:1583)\r\n"
        }
    ],
    "type": "source"
}



curl --location 'http://localhost:8083/connectors' \
--header 'Content-Type: application/json' \
--data '{
    "name": "admin-service-connector",
    "config": {
        "connector.class": "io.debezium.connector.oracle.OracleConnector",
        "database.hostname": "10.177.134.124",
        "database.port": "1590",
        "database.user": "LOHITH_M",
        "database.password": "LOHITH_M",
        "database.dbname": "epaydbdev1",
        "database.server.name": "epay",
        "table.include.list": "epaydbdev1.bankbranches",
        "database.history.kafka.bootstrap.servers": "localhost:9092",
        "database.history.kafka.topic": "schema-changes.bankbranches",
        "topic.prefix": "bankbranches",
        "task.max": "1",
        
        
        
        
        "database.history.producer.bootstrap.servers": "localhost:9092",
        "database.history.consumer.bootstrap.servers": "localhost:9092"
    }
}'

response 

{"name":"admin-service-connector","config":{"connector.class":"io.debezium.connector.oracle.OracleConnector","database.hostname":"10.177.134.124","database.port":"1590","database.user":"LOHITH_M","database.password":"LOHITH_M","database.dbname":"epaydbdev1","database.server.name":"epay","table.include.list":"epaydbdev1.bankbranches","database.history.kafka.bootstrap.servers":"localhost:9092","database.history.kafka.topic":"schema-changes.bankbranches","topic.prefix":"bankbranches","task.max":"1","database.history.producer.bootstrap.servers":"localhost:9092","database.history.consumer.bootstrap.servers":"localhost:9092","name":"admin-service-connector"},"tasks":[],"type":"source"}

C:\kafka_2.13-3.8.1\bin\windows>kafka-topics.bat --list --bootstrap-server localhost:9092
__consumer_offsets
connect-configs
connect-offsets
connect-status
epaydbdev1.bankbranches
schema-changes.bankbranches
