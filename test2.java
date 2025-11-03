<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.io.*,java.net.*,java.util.*,com.fasterxml.jackson.databind.*" %>

<%! 
// Utility: read InputStream to String
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

<html>
<head>
    <title>Transaction Tracker</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        input, button { padding: 8px; margin-top: 10px; }
        .box { border: 1px solid #ccc; padding: 20px; width: 450px; }
    </style>
</head>
<body>

<%
    String txnId = request.getParameter("txnId");
    String apiUrl = "https://yourserver.com/api/txn/track"; // Replace with your endpoint

    if (txnId == null || txnId.trim().equals("")) {
%>
        <div class="box">
            <h3>Enter Transaction ID</h3>
            <form method="get">
                <input type="text" name="txnId" placeholder="Enter Transaction ID" size="30" />
                <button type="submit">Track</button>
            </form>
        </div>
<%
    } else {
        try {
            URL url = new URL(apiUrl + "?txnId=" + URLEncoder.encode(txnId, "UTF-8"));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Origin", request.getScheme() + "://" + request.getServerName());

            int code = conn.getResponseCode();
            String response = "";

            if (code >= 200 && code < 300) {
                response = readStream(conn.getInputStream());
            } else {
                response = readStream(conn.getErrorStream());
            }

            ObjectMapper mapper = new ObjectMapper();
            Map data = mapper.readValue(response, Map.class);

%>
            <div class="box">
                <h2>Transaction Details</h2>
                <p><b>Transaction ID:</b> <%= txnId %></p>
<%
                if (data.get("status") != null) {
%>
                    <p><b>Status:</b> <%= data.get("status") %></p>
<%
                }
                if (data.get("message") != null) {
%>
                    <p><b>Message:</b> <%= data.get("message") %></p>
<%
                }

                if (data.get("details") instanceof List) {
                    List details = (List) data.get("details");
%>
                    <h3>Details:</h3>
                    <ul>
<%
                    for (Object obj : details) {
                        if (obj instanceof Map) {
                            Map d = (Map) obj;
%>
                                <li><%= d %></li>
<%
                        } else {
%>
                                <li><%= obj %></li>
<%
                        }
                    }
%>
                    </ul>
<%
                }
%>
            </div>
<%
        } catch (Exception e) {
%>
            <div class="box">
                <h3>Error occurred:</h3>
                <pre><%= e.getMessage() %></pre>
            </div>
<%
        }
    }
%>

</body>
</html>
