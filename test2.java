<%@ page contentType="text/html;charset=ISO-8859-1" language="java"
         import="java.util.*, java.io.*, java.net.*, org.json.*" %>
<%-- 
   Single-file JSP — works with or without API URL
   If ?apiUrl=<your API> is given, it fetches that JSON.
   If missing or fails, it uses dummy JSON data.
--%>

<%! 
/* Utility: read InputStream to String */
String readStream(InputStream in) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = br.readLine()) != null) {
        sb.append(line);
    }
    return sb.toString();
}
%>

<%
String merchantRefValue = "-";
String sbiepayRefValue = "-";
String bankRefValue = "-";
String transDateRefValue = "-";
String merchantId = "-";
String paymentModeValue = "-";
String captchaValue = "-";
String cardPay = "-";
String gatewayValue = "-";
String ref = "-";
ArrayList<ArrayList<String>> transTrackDetailList1 = new ArrayList<ArrayList<String>>();
int flag = 0;

/* Try API call */
String apiUrl = request.getParameter("apiUrl");
String jsonBody = null;

if (apiUrl != null && apiUrl.trim().length() > 0) {
    HttpURLConnection conn = null;
    InputStream is = null;
    try {
        URL url = new URL(apiUrl.trim());
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(7000);
        conn.setRequestProperty("Accept", "application/json");
        int code = conn.getResponseCode();
        if (code == 200) {
            is = conn.getInputStream();
            jsonBody = readStream(is);
        }
    } catch (Exception e) {
        jsonBody = null;
    } finally {
        try { if (is != null) is.close(); } catch (Exception ex) {}
        if (conn != null) conn.disconnect();
    }
}

/* Parse JSON if available */
boolean parsed = false;
if (jsonBody != null) {
    try {
        JSONObject root = new JSONObject(jsonBody);
        if (root.has("flag")) {
            try { flag = root.getInt("flag"); }
            catch (Exception e) { flag = root.optBoolean("flag") ? 1 : 0; }
        } else if (root.has("allow")) {
            try { flag = root.getInt("allow"); }
            catch (Exception e) { flag = root.optBoolean("allow") ? 1 : 0; }
        }

        merchantRefValue = root.optString("merchantRefValue", merchantRefValue);
        sbiepayRefValue = root.optString("sbiepayRefValue", sbiepayRefValue);
        bankRefValue = root.optString("bankRefValue", bankRefValue);
        transDateRefValue = root.optString("transDateRefValue", transDateRefValue);
        merchantId = root.optString("merchantIdValue", merchantId);
        paymentModeValue = root.optString("paymentModeValue", paymentModeValue);
        captchaValue = root.optString("captchaValue", captchaValue);
        cardPay = root.optString("cardTypeValue", cardPay);
        gatewayValue = root.optString("gatewayValue", gatewayValue);
        ref = root.optString("ref", ref);

        if (root.has("transactions")) {
            JSONArray arr = root.getJSONArray("transactions");
            for (int i = 0; i < arr.length() && i < 3; i++) {
                JSONArray rowArr = arr.getJSONArray(i);
                ArrayList<String> row = new ArrayList<String>();
                for (int j = 0; j < 8; j++) {
                    if (j < rowArr.length()) {
                        row.add(rowArr.getString(j));
                    } else row.add("-");
                }
                transTrackDetailList1.add(row);
            }
        }
        parsed = true;
    } catch (Exception e) {
        parsed = false;
    }
}

/* Dummy data fallback */
if (!parsed || flag != 1) {
    flag = 0;
    merchantRefValue = "MER-123456";
    sbiepayRefValue = "SBIE-654321";
    bankRefValue = "BANK-7890";
    transDateRefValue = "2025-10-30 12:34:56";
    merchantId = "MRC-001";
    paymentModeValue = "CARD";
    captchaValue = "abcd";
    cardPay = "VISA";
    gatewayValue = "GATE-1";
    ref = "REF-DUMMY";

    ArrayList<String> t1 = new ArrayList<String>(Arrays.asList(
        merchantRefValue, sbiepayRefValue, bankRefValue, transDateRefValue, 
        "1000.00", "1000.00", "BOOKED", "Transaction successful"));
    ArrayList<String> t2 = new ArrayList<String>(Arrays.asList(
        "MER-123457", "SBIE-654322", "BANK-7891", "2025-10-29 11:20:00", 
        "250.50", "250.50", "PENDING", "Awaiting bank response"));
    ArrayList<String> t3 = new ArrayList<String>(Arrays.asList(
        "MER-123458", "SBIE-654323", "BANK-7892", "2025-10-28 10:00:00", 
        "500.00", "500.00", "FAILED", "Card declined"));
    transTrackDetailList1.add(t1);
    transTrackDetailList1.add(t2);
    transTrackDetailList1.add(t3);
}

/* Ensure exactly 3 rows */
while (transTrackDetailList1.size() < 3) {
    ArrayList<String> dashRow = new ArrayList<String>();
    for (int i = 0; i < 8; i++) dashRow.add("-");
    transTrackDetailList1.add(dashRow);
}
%>

<!doctype html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Transaction Tracking Details</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.2/css/bootstrap.min.css" rel="stylesheet"/>
    <style>
        body {
            background-image: url(/secure/images/bg.jpg);
            background-size: 100vw 100vh;
        }
        .head { background-color: #000061; color: #fff; text-align: center; }
    </style>
</head>
<body>
<form>
<div class="container mt-4">
    <% if (flag == 1) { %>
        <h4 class="text-center text-primary">Transaction Tracking</h4>
    <% } else { %>
        <div class="alert alert-info text-center">No API response, showing dummy data</div>
    <% } %>

    <table class="table table-bordered table-sm bg-white">
        <thead>
        <tr>
            <th>Particular</th>
            <th>Transaction 1</th>
            <th>Transaction 2</th>
            <th>Transaction 3</th>
        </tr>
        </thead>
        <tbody>
        <tr><th>Merchant Reference Number</th>
            <td><%= transTrackDetailList1.get(0).get(0) %></td>
            <td><%= transTrackDetailList1.get(1).get(0) %></td>
            <td><%= transTrackDetailList1.get(2).get(0) %></td></tr>

        <tr><th>SBIePay Reference Number</th>
            <td><%= transTrackDetailList1.get(0).get(1) %></td>
            <td><%= transTrackDetailList1.get(1).get(1) %></td>
            <td><%= transTrackDetailList1.get(2).get(1) %></td></tr>

        <tr><th>Bank Reference Number</th>
            <td><%= transTrackDetailList1.get(0).get(2) %></td>
            <td><%= transTrackDetailList1.get(1).get(2) %></td>
            <td><%= transTrackDetailList1.get(2).get(2) %></td></tr>

        <tr><th>Transaction Date & Time</th>
            <td><%= transTrackDetailList1.get(0).get(3) %></td>
            <td><%= transTrackDetailList1.get(1).get(3) %></td>
            <td><%= transTrackDetailList1.get(2).get(3) %></td></tr>

        <tr><th>Transaction Amount</th>
            <td><%= transTrackDetailList1.get(0).get(4) %></td>
            <td><%= transTrackDetailList1.get(1).get(4) %></td>
            <td><%= transTrackDetailList1.get(2).get(4) %></td></tr>

        <tr><th>Total Amount</th>
            <td><%= transTrackDetailList1.get(0).get(5) %></td>
            <td><%= transTrackDetailList1.get(1).get(5) %></td>
            <td><%= transTrackDetailList1.get(2).get(5) %></td></tr>

        <tr><th>Transaction Status</th>
            <td><%= transTrackDetailList1.get(0).get(6) %></td>
            <td><%= transTrackDetailList1.get(1).get(6) %></td>
            <td><%= transTrackDetailList1.get(2).get(6) %></td></tr>

        <tr><th>Transaction Status Description</th>
            <td><%= transTrackDetailList1.get(0).get(7) %></td>
            <td><%= transTrackDetailList1.get(1).get(7) %></td>
            <td><%= transTrackDetailList1.get(2).get(7) %></td></tr>
        </tbody>
    </table>
</div>
</form>
</body>
</html>
