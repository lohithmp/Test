<%@ page import="java.net.*, java.io.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>API Data Viewer</title>
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
    <h2>Fetch JSON Placeholder API</h2>

    <form method="get">
        <input type="hidden" name="fetch" value="true">
        <button class="btn" type="submit">Fetch API Data</button>
    </form>

    <div class="data-box">
        <%
            if ("true".equals(request.getParameter("fetch"))) {
                try {
                    URL url = new URL("https://jsonplaceholder.typicode.com/posts/1");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

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
                    <p style="color: red;">Error fetching data: <%= e.getMessage() %></p>
        <%
                }
            } else {
        %>
            <p>Click "Fetch API Data" to view JSONPlaceholder response.</p>
        <%
            }
        %>
    </div>
</div>
</body>
</html>
