<%@ page import="javax.net.ssl.*, java.security.cert.X509Certificate, java.net.*, java.io.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>API Data Viewer (Java 8 + Origin Header)</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f8;
            margin: 40px;
        }
        .container {
            background: white;
            border-radius: 12px;
            padding: 20px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            max-width: 600px;
            margin: auto;
        }
        h2 {
            text-align: center;
            color: #333;
        }
        .data-box {
            background: #f9fafc;
            padding: 15px;
            border-radius: 8px;
            margin-top: 15px;
        }
        pre {
            background: #efefef;
            padding: 10px;
            border-radius: 6px;
            white-space: pre-wrap;
            word-wrap: break-word;
        }
        .btn {
            display: block;
            margin: 20px auto;
            padding: 10px 20px;
            border: none;
            background-color: #0078D7;
            color: white;
            font-size: 16px;
            border-radius: 6px;
            cursor: pointer;
        }
        .btn:hover {
            background-color: #005ea2;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Fetch API Data (Ignore SSL + Origin Header)</h2>

    <form method="get">
        <input type="hidden" name="fetch" value="true">
        <button class="btn" type="submit">Fetch API Data</button>
    </form>

    <div class="data-box">
        <%
            if ("true".equals(request.getParameter("fetch"))) {
                try {
                    // ✅ Your API endpoint
                    String apiUrl = "https://your-domain-or-ip/api/v1/data";

                    // ✅ The Origin header value you want to send
                    String originHeader = "https://yourfrontend.com";

                    // ---- Disable SSL Validation (Java 8 Compatible) ----
                    TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            public X509Certificate[] getAcceptedIssuers() { return null; }
                            public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                            public void checkServerTrusted(X509Certificate[] certs, String authType) { }
                        }
                    };

                    SSLContext sc = SSLContext.getInstance("TLS");
                    sc.init(null, trustAllCerts, new java.security.SecureRandom());
                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

                    HostnameVerifier allHostsValid = new HostnameVerifier() {
                        public boolean verify(String hostname, SSLSession session) { return true; }
                    };
                    HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
                    // ---------------------------------------------------

                    URL url = new URL(apiUrl);
                    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(10000);
                    conn.setReadTimeout(10000);

                    // ✅ Add Origin header (and optional others)
                    conn.setRequestProperty("Origin", originHeader);
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setRequestProperty("User-Agent", "Java-HTTPS-Client");

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuilder content = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();
                    conn.disconnect();
        %>
                    <h3>API Response:</h3>
                    <pre><%= content.toString() %></pre>
        <%
                } catch (Exception e) {
        %>
                    <p style="color: red;">An Error Occurred While Fetching Data:</p>
                    <pre><%= e.toString() %></pre>
        <%
                }
            } else {
        %>
            <p>Click "Fetch API Data" to call your API with Origin header.</p>
        <%
            }
        %>
    </div>
</div>
</body>
</html>
