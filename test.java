{
    "name": "admin-service-connector",
    "config": {
        "connector.class": "io.debezium.connector.oracle.OracleConnector",
        "database.hostname": "10.177.134.124",
        "database.port": "1590",
        "database.user": "LOHITH_M",
        "database.password": "LOHITH_M",
        "database.dbname": "epaydbdev1",
        "database.server.name": "epaydbdev1",
        "schema.include.list": "LOHITH_M",
        "table.include.list": "LOHITH_M.bankbranches, LOHITH_M.AGGMERCHGTWFEESTRUCTURE, LOHITH_M.SERVICETAXSTRUCTURE, LOHITH_M.AGGMERCHANTSERVICETAXMAPPING, LOHITH_M.AGGMERCHANTHYBRIDFEESTRUCTURE",
        "database.history.kafka.bootstrap.servers": "localhost:9092",
        "database.history.kafka.topic": "schema.changes",
        "topic.prefix": "Admin",
        "task.max": "1",
        "schema.history.internal.kafka.bootstrap.servers": "localhost:9092",
        "schema.history.internal.kafka.topic": "schema.changes",

        "database.history.store.only.captured.tables.ddl": true,
        "include.schema.changes":false,

        "value.converter.schemas.enable": false,
        "datetime.handling.mode": "string",
        "decimal.handling.mode": "string",
        "binary.handling.mode": "hex",

        "schema.exclude.list": "ROHINI_K, SHIVANI_R, SHUBHANGI_KURELAY, APEX_180200, HRISHIKESH_PANDIRKAR, BHOOPENDRA_RAJPUT, RAHUL_KUMAR, NIRVAY_BIKRAM, TEST_USER, PANDURANG, SHITAL_SURYAWANSHI, VINOD_BHOSLE, GIREESH_M, AARTI, PAYAGGADMIN, SANDEEP_DESHMUKH, POONAM_R, RAVI_RATHORE, BHUSHAN_WADEKAR"
    }
}
