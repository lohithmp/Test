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


{"before":{"MERCHGTWFEEID":"111112","AGGREGATORID":"","MERCHANTID":"","PAYGTWID":"","OPERATINGMODEID":"","COUNTRYCODE":"","CURRENCYCODE":"","PAYMODECODE":"","PAYPROCID":"","ACCESSMEDIUMCODE":"","PAYPROCTYPE":"","AGGGTWMAPID":"","INSTRUCTIONTYPE":"","TRANSACTIONTYPE":"","FEEPROCESSFLAG":"","SLABFROM":"1.00","SLABTO":"0.00","MERCHANTFEEAPPLICABLE":"","MERCHANTFEETYPE":"","MERCHANTFEE":"0.00","OTHERFEEAPPLICABLE":"","OTHERFEETYPE":"","OTHERFEE":"0.0000","GTWFEEAPPLICABLE":"","GTWFEETYPE":"","GTWFEE":"0.00","AGGSERVICEFEEAPPLICABLE":"","AGGSERVICEFEETYPE":"","AGGSERVICEFEE":"0.00","STATUS":"","RECORDSTATUS":"A","MAKERREMARKS":null,"CHECKERREMARKS":null,"CREATIONDATE":0,"CREATEDBY":"","CREATEDBYSESSIONID":"","MODIFIEDDATE":null,"MODIFIEDBY":null,"MODIFIEDBYSESSIONID":null,"ADJUSTMENTFLAG":null},"after":{"MERCHGTWFEEID":"111112","AGGREGATORID":"","MERCHANTID":"","PAYGTWID":"","OPERATINGMODEID":"","COUNTRYCODE":"","CURRENCYCODE":"","PAYMODECODE":"","PAYPROCID":"","ACCESSMEDIUMCODE":"","PAYPROCTYPE":"","AGGGTWMAPID":"","INSTRUCTIONTYPE":"","TRANSACTIONTYPE":"","FEEPROCESSFLAG":"","SLABFROM":"3.00","SLABTO":"0.00","MERCHANTFEEAPPLICABLE":"","MERCHANTFEETYPE":"","MERCHANTFEE":"0.00","OTHERFEEAPPLICABLE":"","OTHERFEETYPE":"","OTHERFEE":"0.0000","GTWFEEAPPLICABLE":"","GTWFEETYPE":"","GTWFEE":"0.00","AGGSERVICEFEEAPPLICABLE":"","AGGSERVICEFEETYPE":"","AGGSERVICEFEE":"0.00","STATUS":"","RECORDSTATUS":"A","MAKERREMARKS":null,"CHECKERREMARKS":null,"CREATIONDATE":0,"CREATEDBY":"","CREATEDBYSESSIONID":"","MODIFIEDDATE":null,"MODIFIEDBY":null,"MODIFIEDBYSESSIONID":null,"ADJUSTMENTFLAG":null},"source":{"version":"3.0.6.Final","connector":"oracle","name":"Admin","ts_ms":1741182499000,"snapshot":"false","db":"EPAYDBDEV1","sequence":null,"ts_us":1741182499000000,"ts_ns":1741182499000000000,"schema":"LOHITH_M","table":"AGGMERCHGTWFEESTRUCTURE","txId":"0500080076ca0000","scn":"289815835","commit_scn":"289815836","lcr_position":null,"rs_id":"0x0034b1.00143517.0010","ssn":0,"redo_thread":1,"user_name":"LOHITH_M","redo_sql":null,"row_id":"AAAkvCAAIAAFqf/AAD"},"transaction":null,"op":"u","ts_ms":1741182507144,"ts_us":1741182507144726,"ts_ns":1741182507144726700}
