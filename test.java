<%@ page contentType="text/html;charset=ISO-8859-1" language="java" import="java.util.*, java.io.*, java.net.*, javax.json.*" %>
<%-- Single-file JSP: calls API (if provided), else builds dummy JSON data, then renders Transaction Tracking table.
     - Java 8 compatible (no lambdas)
     - Only external dependency: CSS (bootstrap) included below
--%>
<%
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

/* Default variables used by page (matching original JSP names) */
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

/* Structure to hold up to 3 transactions (each transaction is ArrayList of 8 elements like original code) */
ArrayList<ArrayList<String>> transTrackDetailList1 = new ArrayList<ArrayList<String>>();
int flag = 0;

/* Attempt to call API if apiUrl parameter present */
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
        } else {
            // Non-200 — treat as no response
            jsonBody = null;
        }
    } catch (Exception e) {
        // swallow and proceed to dummy
        jsonBody = null;
    } finally {
        try { if (is != null) is.close(); } catch (Exception ex) {}
        if (conn != null) conn.disconnect();
    }
}

/* If we got a JSON body, try to parse it with JSON-P */
boolean parsed = false;
if (jsonBody != null) {
    try {
        JsonReader jr = Json.createReader(new StringReader(jsonBody));
        JsonObject root = jr.readObject();
        jr.close();
        // Try flag or allow
        if (root.containsKey("flag")) {
            // flag might be number or boolean
            try {
                flag = root.getInt("flag");
            } catch (Exception e) {
                try { flag = root.getBoolean("flag") ? 1 : 0; } catch (Exception ex) {}
            }
        } else if (root.containsKey("allow")) {
            try { flag = root.getBoolean("allow") ? 1 : 0; } catch (Exception e) {
                try { flag = root.getInt("allow"); } catch (Exception ex) {}
            }
        }
        // pick basic fields if present
        if (root.containsKey("merchantRefValue")) merchantRefValue = root.getString("merchantRefValue", merchantRefValue);
        if (root.containsKey("sbiepayRefValue")) sbiepayRefValue = root.getString("sbiepayRefValue", sbiepayRefValue);
        if (root.containsKey("bankRefValue")) bankRefValue = root.getString("bankRefValue", bankRefValue);
        if (root.containsKey("transDateRefValue")) transDateRefValue = root.getString("transDateRefValue", transDateRefValue);
        if (root.containsKey("merchantIdValue")) merchantId = root.getString("merchantIdValue", merchantId);
        if (root.containsKey("paymentModeValue")) paymentModeValue = root.getString("paymentModeValue", paymentModeValue);
        if (root.containsKey("captchaValue")) captchaValue = root.getString("captchaValue", captchaValue);
        if (root.containsKey("cardTypeValue")) cardPay = root.getString("cardTypeValue", cardPay);
        if (root.containsKey("gatewayValue")) gatewayValue = root.getString("gatewayValue", gatewayValue);
        if (root.containsKey("ref")) ref = root.getString("ref", ref);

        // transactions array: expect "transactions": [ ["mref","sbie","bank","date","amount","total","status","desc"], ... ]
        if (root.containsKey("transactions")) {
            JsonArray transArr = root.getJsonArray("transactions");
            for (int i = 0; i < transArr.size() && i < 3; i++) {
                JsonArray one = transArr.getJsonArray(i);
                ArrayList<String> row = new ArrayList<String>();
                for (int j = 0; j < 8; j++) { // ensure 8 columns
                    if (j < one.size()) {
                        JsonValue v = one.get(j);
                        if (v.getValueType() == JsonValue.ValueType.STRING) {
                            row.add(one.getString(j, "-"));
                        } else {
                            row.add(one.get(j).toString());
                        }
                    } else {
                        row.add("-");
                    }
                }
                transTrackDetailList1.add(row);
            }
        }
        parsed = true;
    } catch (Exception e) {
        parsed = false;
    }
}

/* If parsing failed or flag not set to 1, create dummy JSON/data */
if (!parsed || flag != 1) {
    flag = 0;
    // Dummy values (you can change these)
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

    // Create up to 3 dummy transactions
    ArrayList<String> t1 = new ArrayList<String>(Arrays.asList(
            merchantRefValue, sbiepayRefValue, bankRefValue, transDateRefValue, "1000.00", "1000.00", "BOOKED", "Transaction successful"
    ));
    ArrayList<String> t2 = new ArrayList<String>(Arrays.asList(
            "MER-123457", "SBIE-654322", "BANK-7891", "2025-10-29 11:20:00", "250.50", "250.50", "PENDING", "Awaiting bank response"
    ));
    ArrayList<String> t3 = new ArrayList<String>(Arrays.asList(
            "MER-123458", "SBIE-654323", "BANK-7892", "2025-10-28 10:00:00", "500.00", "500.00", "FAILED", "Card declined"
    ));
    transTrackDetailList1.add(t1);
    transTrackDetailList1.add(t2);
    transTrackDetailList1.add(t3);
}

/* Ensure exactly 3 rows so JSP table logic works the same as your original */
while (transTrackDetailList1.size() < 3) {
    ArrayList<String> dashRow = new ArrayList<String>();
    for (int i = 0; i < 8; i++) dashRow.add("-");
    transTrackDetailList1.add(dashRow);
}

/* Now flag is set and transTrackDetailList1 is populated */
%>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Transaction Tracking Details</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.2/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link rel="icon" type="image/gif" sizes="16x16" href="/secure/img/favicon-sbiepay.ico" />
    <style>
        body{
            background-image: url(/secure/images/bg.jpg) !important;
            background-size: 100vw 100vh;
            top: 0; left: 0; height: 100vh; width: 100vw;
        }
        .head { background-color: #000061; color: #fff; text-align: center; }
        .headingRecord{ color: #000; font-weight:bold; font-size: 25px; text-align: center; margin: 0 30px; }
        .btnHome{ background: #E0E0E0; margin-left: 100px; }
        .btnBack{ margin-right: 100px; background-color: #000061; color: #fff; }
    </style>
</head>
<body>
<form name="aggATRNDetails" method="get" action="">
    <div class="container">
        <br/><br/>
        <div class="row">
            <% if (flag == 1) { %>
                <table class="table table-bordered table-sm" style="background-color: #fff;">
                    <thead>
                    <tr><th colspan="4" class="head">Transaction Tracking</th></tr>
                    <tr>
                        <th>Particular</th>
                        <th>Transaction 1</th>
                        <% if (!((transTrackDetailList1.get(1)).get(0)).equals("-")) { %>
                            <th>Transaction 2</th>
                        <% } %>
                        <% if (!((transTrackDetailList1.get(2)).get(0)).equals("-")) { %>
                            <th>Transaction 3</th>
                        <% } %>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <th>Merchant Reference Number</th>
                        <td><%= transTrackDetailList1.get(0).get(0) %></td>
                        <% if (!((transTrackDetailList1.get(1)).get(0)).equals("-")) { %>
                            <td><%= transTrackDetailList1.get(1).get(0) %></td>
                        <% } %>
                        <% if (!((transTrackDetailList1.get(2)).get(0)).equals("-")) { %>
                            <td><%= transTrackDetailList1.get(2).get(0) %></td>
                        <% } %>
                    </tr>
                    <tr>
                        <th>SBIePay Reference Number</th>
                        <td><%= transTrackDetailList1.get(0).get(1) %></td>
                        <% if (!((transTrackDetailList1.get(1)).get(0)).equals("-")) { %>
                            <td><%= transTrackDetailList1.get(1).get(1) %></td>
                        <% } %>
                        <% if (!((transTrackDetailList1.get(2)).get(0)).equals("-")) { %>
                            <td><%= transTrackDetailList1.get(2).get(1) %></td>
                        <% } %>
                    </tr>
                    <tr>
                        <th>Bank Reference Number</th>
                        <td><%= transTrackDetailList1.get(0).get(2) %></td>
                        <% if (!((transTrackDetailList1.get(1)).get(0)).equals("-")) { %>
                            <td><%= transTrackDetailList1.get(1).get(2) %></td>
                        <% } %>
                        <% if (!((transTrackDetailList1.get(2)).get(0)).equals("-")) { %>
                            <td><%= transTrackDetailList1.get(2).get(2) %></td>
                        <% } %>
                    </tr>
                    <tr>
                        <th>Transaction Date & Time</th>
                        <td><%= transTrackDetailList1.get(0).get(3) %></td>
                        <% if (!((transTrackDetailList1.get(1)).get(0)).equals("-")) { %>
                            <td><%= transTrackDetailList1.get(1).get(3) %></td>
                        <% } %>
                        <% if (!((transTrackDetailList1.get(2)).get(0)).equals("-")) { %>
                            <td><%= transTrackDetailList1.get(2).get(3) %></td>
                        <% } %>
                    </tr>
                    <tr>
                        <th>Transaction Amount</th>
                        <td><%= transTrackDetailList1.get(0).get(4) %></td>
                        <% if (!((transTrackDetailList1.get(1)).get(0)).equals("-")) { %>
                            <td><%= transTrackDetailList1.get(1).get(4) %></td>
                        <% } %>
                        <% if (!((transTrackDetailList1.get(2)).get(0)).equals("-")) { %>
                            <td><%= transTrackDetailList1.get(2).get(4) %></td>
                        <% } %>
                    </tr>
                    <tr>
                        <th>Total Amount</th>
                        <td><%= transTrackDetailList1.get(0).get(5) %></td>
                        <% if (!((transTrackDetailList1.get(1)).get(0)).equals("-")) { %>
                            <td><%= transTrackDetailList1.get(1).get(5) %></td>
                        <% } %>
                        <% if (!((transTrackDetailList1.get(2)).get(0)).equals("-")) { %>
                            <td><%= transTrackDetailList1.get(2).get(5) %></td>
                        <% } %>
                    </tr>
                    <tr>
                        <th>Transaction Status</th>
                        <td><%= transTrackDetailList1.get(0).get(6) %></td>
                        <% if (!((transTrackDetailList1.get(1)).get(0)).equals("-")) { %>
                            <td><%= transTrackDetailList1.get(1).get(6) %></td>
                        <% } %>
                        <% if (!((transTrackDetailList1.get(2)).get(0)).equals("-")) { %>
                            <td><%= transTrackDetailList1.get(2).get(6) %></td>
                        <% } %>
                    </tr>
                    <tr>
                        <th>Transaction Status Description</th>
                        <td><%= transTrackDetailList1.get(0).get(7) %></td>
                        <% if (!((transTrackDetailList1.get(1)).get(0)).equals("-")) { %>
                            <td><%= transTrackDetailList1.get(1).get(7) %></td>
                        <% } %>
                        <% if (!((transTrackDetailList1.get(2)).get(0)).equals("-")) { %>
                            <td><%= transTrackDetailList1.get(2).get(7) %></td>
                        <% } %>
                    </tr>
                    </tbody>
                </table>
            <% } else { %>
                <div class="col-12">
                    <div class="alert alert-info">
                        <strong>No transaction data from API.</strong> Showing sample/dummy data below.
                    </div>

                    <table class="table table-bordered table-sm" style="background-color: #fff;">
                        <thead><tr><th colspan="4" class="head">Transaction Tracking (Dummy Data)</th></tr></thead>
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
            <% } %>
        </div>
    </div>
</form>
</body>
</html>
