 String txnResponse = "{\"status\": 1, \"data\": [{\"merchantReferenceNumber\": \"268280741\", \"sbiEpayReferenceNumber\": \"1F9FF78F3A644EE79CFF\", \"bankReferenceNumber\": \"268280741\", \"transactionDateAndTime\": 1757400860154, \"transactionAmount\": 1, \"totalAmount\": 2.18, \"transactionStatus\": \"SUCCESS\", \"transactionStatusDescription\": \"SUCCESS\", \"settlementDate\": null, \"cinNumber\": \"10000030909202563108\", \"payMode\": \"CC\", \"channelBank\": \"OTHERS\", \"cardType\": \"MASTER\", \"refundDetails\": [{\"refundReferenceNumber\": \"DC003174590772767481\", \"refundBookingDateAndTime\": 1745907729125, \"refundType\": \"PARTIAL\", \"refundAmount\": 0.01, \"refundStatus\": \"REFUND_PROCESSED\", \"refundProcessedDateAndTime\": 1757412480523}], \"count\": 1, \"total\": 1}]}";

    ObjectMapper mapper = new ObjectMapper();

    Map<String, Object> txnDetails1 = mapper.readValue(
        txnResponse,
        new TypeReference<Map<String, Object>>(){}
    );

    ArrayList dataList = (ArrayList) txnDetails1.get("data");

    if (!dataList.isEmpty()) {
    }

    ArrayList aggRefundResultList = new ArrayList();


	ArrayList transTrackGetList = new ArrayList();
	ArrayList transTrackDetailList = new ArrayList();
	
    int size = 0;
    int listSize = 0;
    int flag = 0;

    	try	{
        		transTrackGetList = dataList;
        		listSize = transTrackGetList.size();

        		if(listSize>0) {
        			flag = 1;
        		    for(int i=0; listSize>i; i++) {
                        transTrackDetailList = (ArrayList) transTrackGetList.get(i);
        		    }
        	    }

                out.println("<pre>transTrackDetailList: "+transTrackDetailList+"</pre>");
            }
        catch(Exception t)  {
        }


        if(listSize>0) {
         	for(int i=0; i<transTrackDetailList.size(); i++) {
         	    if(transTrackDetailList.get("refundDetails") && transTrackDetailList.get("refundDetails").get(i) && transTrackDetailList.get("refundDetails").get(i).size()>0) {
                    aggRefundResultList = transTrackDetailList.refundDetails.get(i);
                } else {
                    aggRefundResultList = 0;
                }
        	}
        } else {
        	flag = 0;
        }




The method get(int) in the type ArrayList is not applicable for the arguments (String)

