<%@ page import="java.io.*, java.net.*, java.util.*, javax.net.ssl.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Transaction Track API</title>
</head>
<body>
<h2>Transaction Tracking API Response</h2>

<%
    String apiUrl = "https://your-api-domain.com/your-endpoint"; // change to your actual URL
    String responseContent = "";

    try {
        // --- Create an SSL context that trusts all certificates ---
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
                public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) { }
                public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) { }
            }
        };

        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Disable hostname verification
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        // --- Open connection ---
        URL url = new URL(apiUrl);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Origin", "https://your-domain.com"); // your origin value
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);

        int status = conn.getResponseCode();
        out.println("<p><b>Response Code:</b> " + status + " - " + conn.getResponseMessage() + "</p>");

        // --- Read the response ---
        BufferedReader in;
        if (status >= 200 && status < 400) {
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine).append("\n");
        }
        in.close();
        conn.disconnect();

        responseContent = content.toString();

    } catch (Exception e) {
        responseContent = "An error occurred while fetching data:<br><pre>" + e.toString() + "</pre>";
    }
%>

<h3>Response:</h3>
<pre><%= responseContent %></pre>

</body>
</html>
