{
    "payload": {
        "after": {
            "ACCESSMEDIUMCODE": "ONLINE",
            "ADJUSTMENTFLAG": null,
            "AGGGTWMAPID": "174",
            "AGGREGATORID": "SBIEPAY",
            "AGGSERVICEFEE": "AA==",
            "AGGSERVICEFEEAPPLICABLE": "N",
            "AGGSERVICEFEETYPE": "F",
            "CHECKERREMARKS": "ok",
            "COUNTRYCODE": "IN",
            "CREATEDBY": "opsmaker1@sbiepay.com",
            "CREATEDBYSESSIONID": "3cdQCfpILJr1eeKu0xqO6wdXzWNsljEghe7ATHDD",
            "CREATIONDATE": 1603843200000,
            "CURRENCYCODE": "INR",
            "FEEPROCESSFLAG": "M",
            "GTWFEE": "AA==",
            "GTWFEEAPPLICABLE": "N",
            "GTWFEETYPE": "F",
            "INSTRUCTIONTYPE": "I",
            "MAKERREMARKS": "New Server test",
            "MERCHANTFEE": "AfQ=",
            "MERCHANTFEEAPPLICABLE": "Y",
            "MERCHANTFEETYPE": "F",
            "MERCHANTID": "636108808",
            "MERCHGTWFEEID": "56446",
            "MODIFIEDBY": "opschecker1@sbiepay.com",
            "MODIFIEDBYSESSIONID": "7k7kLITDXLJHY6Gfl68ofgzyH9Ho9dWYPXv_RW-v",
            "MODIFIEDDATE": 1603843200000,
            "OPERATINGMODEID": "DOM",
            "OTHERFEE": "AA==",
            "OTHERFEEAPPLICABLE": "N",
            "OTHERFEETYPE": "F",
            "PAYGTWID": "138",
            "PAYMODECODE": "NB",
            "PAYPROCID": "SELF",
            "PAYPROCTYPE": "ONUS",
            "RECORDSTATUS": "A",
            "SLABFROM": "AMg=",
            "SLABTO": "I4byb8D/nA==",
            "STATUS": "A",
            "TRANSACTIONTYPE": "ORDER"
        },
        "before": {
            "ACCESSMEDIUMCODE": "ONLINE",
            "ADJUSTMENTFLAG": null,
            "AGGGTWMAPID": "174",
            "AGGREGATORID": "SBIEPAY",
            "AGGSERVICEFEE": "AA==",
            "AGGSERVICEFEEAPPLICABLE": "N",
            "AGGSERVICEFEETYPE": "F",
            "CHECKERREMARKS": "ok",
            "COUNTRYCODE": "IN",
            "CREATEDBY": "opsmaker1@sbiepay.com",
            "CREATEDBYSESSIONID": "3cdQCfpILJr1eeKu0xqO6wdXzWNsljEghe7ATHDD",
            "CREATIONDATE": 1603843200000,
            "CURRENCYCODE": "INR",
            "FEEPROCESSFLAG": "M",
            "GTWFEE": "AA==",
            "GTWFEEAPPLICABLE": "N",
            "GTWFEETYPE": "F",
            "INSTRUCTIONTYPE": "I",
            "MAKERREMARKS": "New Server test",
            "MERCHANTFEE": "AfQ=",
            "MERCHANTFEEAPPLICABLE": "Y",
            "MERCHANTFEETYPE": "F",
            "MERCHANTID": "636108808",
            "MERCHGTWFEEID": "56446",
            "MODIFIEDBY": "opschecker1@sbiepay.com",
            "MODIFIEDBYSESSIONID": "7k7kLITDXLJHY6Gfl68ofgzyH9Ho9dWYPXv_RW-v",
            "MODIFIEDDATE": 1603843200000,
            "OPERATINGMODEID": "DOM",
            "OTHERFEE": "AA==",
            "OTHERFEEAPPLICABLE": "N",
            "OTHERFEETYPE": "F",
            "PAYGTWID": "138",
            "PAYMODECODE": "NB",
            "PAYPROCID": "SELF",
            "PAYPROCTYPE": "ONUS",
            "RECORDSTATUS": "A",
            "SLABFROM": "ZA==",
            "SLABTO": "I4byb8D/nA==",
            "STATUS": "A",
            "TRANSACTIONTYPE": "ORDER"
        },
        "op": "u",
        "source": {
            "commit_scn": "361326982",
            "connector": "oracle",
            "db": "EPAYDBDEV1",
            "lcr_position": null,
            "name": "cdc-oracle-gemfire",
            "redo_sql": null,
            "redo_thread": 1,
            "row_id": "AAAkvCAAIAAFdnrAAA",
            "rs_id": "0x0037b0.0019521d.0010",
            "schema": "LOHITH_M",
            "scn": "361326981",
            "sequence": null,
            "snapshot": "false",
            "ssn": 0,
            "table": "AGGMERCHGTWFEESTRUCTURE",
            "ts_ms": 1745046563000,
            "ts_ns": 1745046563000000000,
            "ts_us": 1745046563000000,
            "txId": "05000e00defe0000",
            "user_name": "LOHITH_M",
            "version": "3.0.8.Final-redhat-00004"
        },
        "transaction": null,
        "ts_ms": 1745046567157,
        "ts_ns": 1745046567157366000,
        "ts_us": 1745046567157366
    },
    "schema": {
        "fields": [
            {
                "field": "before",
                "fields": [
                    {
                        "field": "MERCHGTWFEEID",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "AGGREGATORID",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "MERCHANTID",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "PAYGTWID",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "OPERATINGMODEID",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "COUNTRYCODE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "CURRENCYCODE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "PAYMODECODE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "PAYPROCID",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "ACCESSMEDIUMCODE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "PAYPROCTYPE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "AGGGTWMAPID",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "INSTRUCTIONTYPE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "TRANSACTIONTYPE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "FEEPROCESSFLAG",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "SLABFROM",
                        "name": "org.apache.kafka.connect.data.Decimal",
                        "optional": false,
                        "parameters": {
                            "connect.decimal.precision": "16",
                            "scale": "2"
                        },
                        "type": "bytes",
                        "version": 1
                    },
                    {
                        "field": "SLABTO",
                        "name": "org.apache.kafka.connect.data.Decimal",
                        "optional": false,
                        "parameters": {
                            "connect.decimal.precision": "16",
                            "scale": "2"
                        },
                        "type": "bytes",
                        "version": 1
                    },
                    {
                        "field": "MERCHANTFEEAPPLICABLE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "MERCHANTFEETYPE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "MERCHANTFEE",
                        "name": "org.apache.kafka.connect.data.Decimal",
                        "optional": false,
                        "parameters": {
                            "connect.decimal.precision": "16",
                            "scale": "2"
                        },
                        "type": "bytes",
                        "version": 1
                    },
                    {
                        "field": "OTHERFEEAPPLICABLE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "OTHERFEETYPE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "OTHERFEE",
                        "name": "org.apache.kafka.connect.data.Decimal",
                        "optional": false,
                        "parameters": {
                            "connect.decimal.precision": "16",
                            "scale": "4"
                        },
                        "type": "bytes",
                        "version": 1
                    },
                    {
                        "field": "GTWFEEAPPLICABLE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "GTWFEETYPE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "GTWFEE",
                        "name": "org.apache.kafka.connect.data.Decimal",
                        "optional": false,
                        "parameters": {
                            "connect.decimal.precision": "16",
                            "scale": "2"
                        },
                        "type": "bytes",
                        "version": 1
                    },
                    {
                        "field": "AGGSERVICEFEEAPPLICABLE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "AGGSERVICEFEETYPE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "AGGSERVICEFEE",
                        "name": "org.apache.kafka.connect.data.Decimal",
                        "optional": false,
                        "parameters": {
                            "connect.decimal.precision": "16",
                            "scale": "2"
                        },
                        "type": "bytes",
                        "version": 1
                    },
                    {
                        "field": "STATUS",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "RECORDSTATUS",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "MAKERREMARKS",
                        "optional": true,
                        "type": "string"
                    },
                    {
                        "field": "CHECKERREMARKS",
                        "optional": true,
                        "type": "string"
                    },
                    {
                        "field": "CREATIONDATE",
                        "name": "io.debezium.time.Timestamp",
                        "optional": false,
                        "type": "int64",
                        "version": 1
                    },
                    {
                        "field": "CREATEDBY",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "CREATEDBYSESSIONID",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "MODIFIEDDATE",
                        "name": "io.debezium.time.Timestamp",
                        "optional": true,
                        "type": "int64",
                        "version": 1
                    },
                    {
                        "field": "MODIFIEDBY",
                        "optional": true,
                        "type": "string"
                    },
                    {
                        "field": "MODIFIEDBYSESSIONID",
                        "optional": true,
                        "type": "string"
                    },
                    {
                        "field": "ADJUSTMENTFLAG",
                        "optional": true,
                        "type": "string"
                    }
                ],
                "name": "cdc-oracle-gemfire.LOHITH_M.AGGMERCHGTWFEESTRUCTURE.Value",
                "optional": true,
                "type": "struct"
            },
            {
                "field": "after",
                "fields": [
                    {
                        "field": "MERCHGTWFEEID",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "AGGREGATORID",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "MERCHANTID",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "PAYGTWID",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "OPERATINGMODEID",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "COUNTRYCODE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "CURRENCYCODE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "PAYMODECODE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "PAYPROCID",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "ACCESSMEDIUMCODE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "PAYPROCTYPE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "AGGGTWMAPID",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "INSTRUCTIONTYPE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "TRANSACTIONTYPE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "FEEPROCESSFLAG",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "SLABFROM",
                        "name": "org.apache.kafka.connect.data.Decimal",
                        "optional": false,
                        "parameters": {
                            "connect.decimal.precision": "16",
                            "scale": "2"
                        },
                        "type": "bytes",
                        "version": 1
                    },
                    {
                        "field": "SLABTO",
                        "name": "org.apache.kafka.connect.data.Decimal",
                        "optional": false,
                        "parameters": {
                            "connect.decimal.precision": "16",
                            "scale": "2"
                        },
                        "type": "bytes",
                        "version": 1
                    },
                    {
                        "field": "MERCHANTFEEAPPLICABLE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "MERCHANTFEETYPE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "MERCHANTFEE",
                        "name": "org.apache.kafka.connect.data.Decimal",
                        "optional": false,
                        "parameters": {
                            "connect.decimal.precision": "16",
                            "scale": "2"
                        },
                        "type": "bytes",
                        "version": 1
                    },
                    {
                        "field": "OTHERFEEAPPLICABLE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "OTHERFEETYPE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "OTHERFEE",
                        "name": "org.apache.kafka.connect.data.Decimal",
                        "optional": false,
                        "parameters": {
                            "connect.decimal.precision": "16",
                            "scale": "4"
                        },
                        "type": "bytes",
                        "version": 1
                    },
                    {
                        "field": "GTWFEEAPPLICABLE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "GTWFEETYPE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "GTWFEE",
                        "name": "org.apache.kafka.connect.data.Decimal",
                        "optional": false,
                        "parameters": {
                            "connect.decimal.precision": "16",
                            "scale": "2"
                        },
                        "type": "bytes",
                        "version": 1
                    },
                    {
                        "field": "AGGSERVICEFEEAPPLICABLE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "AGGSERVICEFEETYPE",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "AGGSERVICEFEE",
                        "name": "org.apache.kafka.connect.data.Decimal",
                        "optional": false,
                        "parameters": {
                            "connect.decimal.precision": "16",
                            "scale": "2"
                        },
                        "type": "bytes",
                        "version": 1
                    },
                    {
                        "field": "STATUS",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "RECORDSTATUS",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "MAKERREMARKS",
                        "optional": true,
                        "type": "string"
                    },
                    {
                        "field": "CHECKERREMARKS",
                        "optional": true,
                        "type": "string"
                    },
                    {
                        "field": "CREATIONDATE",
                        "name": "io.debezium.time.Timestamp",
                        "optional": false,
                        "type": "int64",
                        "version": 1
                    },
                    {
                        "field": "CREATEDBY",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "CREATEDBYSESSIONID",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "MODIFIEDDATE",
                        "name": "io.debezium.time.Timestamp",
                        "optional": true,
                        "type": "int64",
                        "version": 1
                    },
                    {
                        "field": "MODIFIEDBY",
                        "optional": true,
                        "type": "string"
                    },
                    {
                        "field": "MODIFIEDBYSESSIONID",
                        "optional": true,
                        "type": "string"
                    },
                    {
                        "field": "ADJUSTMENTFLAG",
                        "optional": true,
                        "type": "string"
                    }
                ],
                "name": "cdc-oracle-gemfire.LOHITH_M.AGGMERCHGTWFEESTRUCTURE.Value",
                "optional": true,
                "type": "struct"
            },
            {
                "field": "source",
                "fields": [
                    {
                        "field": "version",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "connector",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "name",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "ts_ms",
                        "optional": false,
                        "type": "int64"
                    },
                    {
                        "default": "false",
                        "field": "snapshot",
                        "name": "io.debezium.data.Enum",
                        "optional": true,
                        "parameters": {
                            "allowed": "true,first,first_in_data_collection,last_in_data_collection,last,false,incremental"
                        },
                        "type": "string",
                        "version": 1
                    },
                    {
                        "field": "db",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "sequence",
                        "optional": true,
                        "type": "string"
                    },
                    {
                        "field": "ts_us",
                        "optional": true,
                        "type": "int64"
                    },
                    {
                        "field": "ts_ns",
                        "optional": true,
                        "type": "int64"
                    },
                    {
                        "field": "schema",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "table",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "txId",
                        "optional": true,
                        "type": "string"
                    },
                    {
                        "field": "scn",
                        "optional": true,
                        "type": "string"
                    },
                    {
                        "field": "commit_scn",
                        "optional": true,
                        "type": "string"
                    },
                    {
                        "field": "lcr_position",
                        "optional": true,
                        "type": "string"
                    },
                    {
                        "field": "rs_id",
                        "optional": true,
                        "type": "string"
                    },
                    {
                        "field": "ssn",
                        "optional": true,
                        "type": "int64"
                    },
                    {
                        "field": "redo_thread",
                        "optional": true,
                        "type": "int32"
                    },
                    {
                        "field": "user_name",
                        "optional": true,
                        "type": "string"
                    },
                    {
                        "field": "redo_sql",
                        "optional": true,
                        "type": "string"
                    },
                    {
                        "field": "row_id",
                        "optional": true,
                        "type": "string"
                    }
                ],
                "name": "io.debezium.connector.oracle.Source",
                "optional": false,
                "type": "struct"
            },
            {
                "field": "transaction",
                "fields": [
                    {
                        "field": "id",
                        "optional": false,
                        "type": "string"
                    },
                    {
                        "field": "total_order",
                        "optional": false,
                        "type": "int64"
                    },
                    {
                        "field": "data_collection_order",
                        "optional": false,
                        "type": "int64"
                    }
                ],
                "name": "event.block",
                "optional": true,
                "type": "struct",
                "version": 1
            },
            {
                "field": "op",
                "optional": false,
                "type": "string"
            },
            {
                "field": "ts_ms",
                "optional": true,
                "type": "int64"
            },
            {
                "field": "ts_us",
                "optional": true,
                "type": "int64"
            },
            {
                "field": "ts_ns",
                "optional": true,
                "type": "int64"
            }
        ],
        "name": "cdc-oracle-gemfire.LOHITH_M.AGGMERCHGTWFEESTRUCTURE.Envelope",
        "optional": false,
        "type": "struct",
        "version": 2
    }
}
