<%@ page import="java.util.*, com.fasterxml.jackson.databind.*, com.fasterxml.jackson.core.type.TypeReference" %>
<html>
<head>
    <title>Transaction and Refund Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background: #f8f9fa;
        }
        h2 {
            color: #333;
            margin-bottom: 10px;
        }
        table {
            border-collapse: collapse;
            width: 100%;
            background-color: #fff;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin-bottom: 30px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px 12px;
            text-align: left;
        }
        th {
            background-color: #007bff;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        .no-data {
            color: red;
            font-weight: bold;
        }
    </style>
</head>
<body>

<h2>Transaction and Refund Details</h2>

<%
ArrayList<Map<String, Object>> aggRefundResultList = new ArrayList<>();
ArrayList<Map<String, Object>> refundDetailsList = new ArrayList<>();
int flag = 0;

try {
    String txnResponse = "{\"status\": 1, \"data\": [{\"merchantReferenceNumber\": \"268280741\", \"sbiEpayReferenceNumber\": \"1F9FF78F3A644EE79CFF\", \"bankReferenceNumber\": \"268280741\", \"transactionDateAndTime\": 1757400860154, \"transactionAmount\": 1, \"totalAmount\": 2.18, \"transactionStatus\": \"SUCCESS\", \"transactionStatusDescription\": \"SUCCESS\", \"settlementDate\": null, \"cinNumber\": \"10000030909202563108\", \"payMode\": \"CC\", \"channelBank\": \"OTHERS\", \"cardType\": \"MASTER\", \"refundDetails\": [{\"refundReferenceNumber\": \"DC003174590772767481\", \"refundBookingDateAndTime\": 1745907729125, \"refundType\": \"PARTIAL\", \"refundAmount\": 0.01, \"refundStatus\": \"REFUND_PROCESSED\", \"refundProcessedDateAndTime\": 1757412480523}], \"count\": 1, \"total\": 1}]}";

    ObjectMapper mapper = new ObjectMapper();

    Map<String, Object> txnDetails1 = mapper.readValue(
        txnResponse,
        new TypeReference<Map<String, Object>>() {}
    );

    ArrayList dataList = (ArrayList) txnDetails1.get("data");

    if (dataList != null && !dataList.isEmpty()) {
        flag = 1;
        for (int i = 0; i < dataList.size(); i++) {
            Map<String, Object> transaction = (Map<String, Object>) dataList.get(i);

            // Print Transaction details
%>
            <h3>Transaction <%= (i + 1) %> Details</h3>
            <table>
                <tr><th>Merchant Reference</th><td><%= transaction.get("merchantReferenceNumber") %></td></tr>
                <tr><th>SBI Epay Reference</th><td><%= transaction.get("sbiEpayReferenceNumber") %></td></tr>
                <tr><th>Bank Reference</th><td><%= transaction.get("bankReferenceNumber") %></td></tr>
                <tr><th>Transaction Amount</th><td><%= transaction.get("transactionAmount") %></td></tr>
                <tr><th>Total Amount</th><td><%= transaction.get("totalAmount") %></td></tr>
                <tr><th>Status</th><td><%= transaction.get("transactionStatus") %></td></tr>
                <tr><th>Status Description</th><td><%= transaction.get("transactionStatusDescription") %></td></tr>
                <tr><th>Pay Mode</th><td><%= transaction.get("payMode") %></td></tr>
                <tr><th>Channel Bank</th><td><%= transaction.get("channelBank") %></td></tr>
                <tr><th>Card Type</th><td><%= transaction.get("cardType") %></td></tr>
                <tr><th>CIN Number</th><td><%= transaction.get("cinNumber") %></td></tr>
            </table>
<%
            // Handle Refund Details
            refundDetailsList = (ArrayList<Map<String, Object>>) transaction.get("refundDetails");
            if (refundDetailsList != null && !refundDetailsList.isEmpty()) {
                aggRefundResultList.addAll(refundDetailsList);
            }
        }
    }

} catch (Exception e) {
    out.println("<p class='no-data'>Error while processing transaction: " + e + "</p>");
}

// Refund Details Display
try {
    if (!aggRefundResultList.isEmpty()) {
%>
        <h3>Refund Details</h3>
        <table>
            <tr>
                <th>Refund Reference Number</th>
                <th>Refund Type</th>
                <th>Refund Amount</th>
                <th>Refund Status</th>
                <th>Refund Booking Date</th>
                <th>Refund Processed Date</th>
            </tr>
<%
        for (int i = 0; i < aggRefundResultList.size(); i++) {
            Map<String, Object> refundMap = (Map<String, Object>) aggRefundResultList.get(i);
%>
            <tr>
                <td><%= refundMap.get("refundReferenceNumber") %></td>
                <td><%= refundMap.get("refundType") %></td>
                <td><%= refundMap.get("refundAmount") %></td>
                <td><%= refundMap.get("refundStatus") %></td>
                <td><%= refundMap.get("refundBookingDateAndTime") %></td>
                <td><%= refundMap.get("refundProcessedDateAndTime") %></td>
            </tr>
<%
        }
%>
        </table>
<%
    } else {
        out.println("<p class='no-data'>No refund details available.</p>");
    }
} catch (Exception e) {
    out.println("<p class='no-data'>Error while processing refund details: " + e + "</p>");
}
%>

</body>
</html>
