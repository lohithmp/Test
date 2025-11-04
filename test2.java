<%@ page import="java.util.*, com.fasterxml.jackson.databind.*, com.fasterxml.jackson.core.type.TypeReference" %>

<%
try {
    String txnResponse = "{\"status\": 1, \"data\": [{\"merchantReferenceNumber\": \"268280741\", \"sbiEpayReferenceNumber\": \"1F9FF78F3A644EE79CFF\", \"bankReferenceNumber\": \"268280741\", \"transactionDateAndTime\": 1757400860154, \"transactionAmount\": 1, \"totalAmount\": 2.18, \"transactionStatus\": \"SUCCESS\", \"transactionStatusDescription\": \"SUCCESS\", \"settlementDate\": null, \"cinNumber\": \"10000030909202563108\", \"payMode\": \"CC\", \"channelBank\": \"OTHERS\", \"cardType\": \"MASTER\", \"refundDetails\": [{\"refundReferenceNumber\": \"DC003174590772767481\", \"refundBookingDateAndTime\": 1745907729125, \"refundType\": \"PARTIAL\", \"refundAmount\": 0.01, \"refundStatus\": \"REFUND_PROCESSED\", \"refundProcessedDateAndTime\": 1757412480523}], \"count\": 1, \"total\": 1}]}";

    ObjectMapper mapper = new ObjectMapper();

    Map<String, Object> txnDetails1 = mapper.readValue(
        txnResponse,
        new TypeReference<Map<String, Object>>() {}
    );

    ArrayList dataList = (ArrayList) txnDetails1.get("data");
    ArrayList aggRefundResultList = new ArrayList();

    if (dataList != null && !dataList.isEmpty()) {
        for (int i = 0; i < dataList.size(); i++) {
            Map<String, Object> transaction = (Map<String, Object>) dataList.get(i);

            out.println("<pre>Transaction: " + transaction + "</pre>");

            if (transaction.containsKey("refundDetails")) {
                ArrayList<Map<String, Object>> refundDetails = (ArrayList<Map<String, Object>>) transaction.get("refundDetails");

                if (refundDetails != null && !refundDetails.isEmpty()) {
                    aggRefundResultList.addAll(refundDetails);
                }
            }
        }
    }

    out.println("<pre>aggRefundResultList: " + aggRefundResultList + "</pre>");

} catch (Exception e) {
    out.println("<pre style='color:red;'>Error: " + e + "</pre>");
}
%>
