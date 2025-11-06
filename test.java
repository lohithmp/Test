<%@ page import="java.io.*, java.net.*, java.nio.charset.StandardCharsets, javax.net.ssl.*, java.security.cert.X509Certificate" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Transaction Tracking</title>
</head>
<body>
<%
    String apiUrl = "https://sit.epay.sbi/api/reporting/v1/transaction/track?page=0&size=2";
    String bearerToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjpbIlZJRVdfQUNDRVNTIl0sInRva2VuVHlwZSI6IlZJRVdfQUNDRVNTIiwidmlld0FjY2Vzc1JlcXVlc3RJZGVudGlmaWVyIjoiOTk4ODAwODg3NyIsInVzZXJuYW1lIjoiOTk4ODAwODg3NyIsInN1YiI6Ijk5ODgwMDg4NzciLCJpc3MiOiJzYmkuZXBheSIsImlhdCI6MTc2MjQzODA3NiwiZXhwIjoxNzYyNDM4Mzc2fQ.TUrleJB-_XoyRY9aIMnMLEsrtEi1ygUnFQbVD2_TXEu2O5Z1wS18kxN-CRpKSjMtc0ulQZMoKkEYHvjPvnZIQ";

    // Disable SSL certificate validation (for SIT/test only)
    try {
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() { return null; }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                public void checkServerTrusted(X509Certificate[] certs, String authType) {}
            }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Disable hostname verification
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
    } catch (Exception e) {
        out.println("<p style='color:red;'>Error disabling SSL check: " + e.getMessage() + "</p>");
    }

    String jsonInputString = "{"
        + "\"referenceNum\": \"NB003175386057119642\","
        + "\"transactionDate\": \"1753860578531\","
        + "\"requestId\": \"34fe10df-4e1f-4b0a-b57a-eaefa7d75555\","
        + "\"captchaHash\": \"IQDhDT5DXnJ2SB6U6IWFHEIiXIxykRkTegKYuL0kY7zMWi8YctIwxEXIm/PIAGMrfN+q3So62/5vjnPIGgiAPA==\""
        + "}";

    String responseBody = "";
    int responseCode = 0;

    try {
        URL url = new URL(apiUrl);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", bearerToken);
        conn.setRequestProperty("Cookie", "DummyCookie=cookieValue");
        conn.setDoOutput(true);

        // Send JSON body
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // Read response
        responseCode = conn.getResponseCode();
        InputStream responseStream = (responseCode >= 200 && responseCode < 300)
            ? conn.getInputStream()
            : conn.getErrorStream();

        BufferedReader in = new BufferedReader(new InputStreamReader(responseStream, StandardCharsets.UTF_8));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            content.append(line);
        }
        in.close();
        conn.disconnect();

        responseBody = content.toString();

    } catch (Exception e) {
        out.println("<p style='color:red;'>An Error Occurred While Fetching Data:<br/>" + e.toString() + "</p>");
    }
%>

<h2>Transaction Tracking API Response</h2>
<p><b>Response Code:</b> <%= responseCode %></p>
<pre><%= responseBody %></pre>

</body>
</html>
