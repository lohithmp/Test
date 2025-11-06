URL url = new URL("https://sit.epay.sbi/api/reporting/v1/transaction/track?page=0&size=2");

                   // 2. Open the connection and set the request method to POST
                   HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                   conn.setRequestMethod("POST");

                   // 3. Set request headers exactly as specified in the curl command
                   conn.setRequestProperty("Content-Type", "application/json");
                   conn.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjpbIlZJRVdfQUNDRVNTIl0sInRva2VuVHlwZSI6IlZJRVdfQUNDRVNTIiwidmlld0FjY2Vzc1JlcXVlc3RJZGVudGlmaWVyIjoiOTk4ODAwODg3NyIsInVzZXJuYW1lIjoiOTk4ODAwODg3NyIsInN1YiI6Ijk5ODgwMDg4NzciLCJpc3MiOiJzYmkuZXBheSIsImlhdCI6MTc2MjQzODA3NiwiZXhwIjoxNzYyNDM4Mzc2fQ.TUrleJB-_XoyRY9aIMnMLEsrtEi1ygUnFQbVD2_TXEu2O5Z1wS18kxN-CRpKSjMtc0ulQZMoKkEYHvjPvnZIQ"); // Ensure this token matches the curl exactly
                   conn.setRequestProperty("Cookie", "DummyCookie=cookieValue; KR019871f6=01890e9c1399d82bba99f7caf85ff8b8ee000c5d0c1a084baecd5d070f060a844f5ce2741f026e255f957f0f17ce99d2d1aecf4bb6ffd511f51617d1b1fed1affa36addaad4e2a0899b5807f55be448a79c2f90fa2; d30df95dc1f9357f00ba4f5303fa3ad3=54ed05e71ba55c7e6fcb12398e40b83d");

                   // 4. Enable output to write the request body
                   conn.setDoOutput(true);

                   // 5. Define the JSON request body exactly as specified in the curl command
                   String jsonInputString = "{"
                           + "\"referenceNum\": \"NB003175386057119642\","
                           + "\"transactionDate\": \"1753860578531\","
                           + "\"requestId\": \"34fe10df-4e1f-4b0a-b57a-eaefa7d75555\"," // Matches curl requestId
                           + "\"captchaHash\": \"IQDhDT5DXnJ2SB6U6IWFHEIiXIxykRkTegKYuL0kY7zMWi8YctIwxEXIm/PIAGMrfN+q3So62/5vjnPIGgiAPA==\"" // Matches curl captchaHash
                           + "}";

                   // 6. Write the JSON data to the request body
                   try (OutputStream os = conn.getOutputStream()) {
                       byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                       os.write(input, 0, input.length);
                   }

                   // 7. Read the response from the server
                   int responseCode = conn.getResponseCode();
           %>
                   <p>Response Code: **`<%= responseCode %>`**</p>
           <%
                   BufferedReader in;
                   if (responseCode >= 200 && responseCode < 300) {
                       in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                   } else {
                       in = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
                   }

                   String inputLine;
                   StringBuilder content = new StringBuilder();
                   while ((inputLine = in.readLine()) != null) {
                       content.append(inputLine);
                   }
                   in.close();

                   txnResponse = content.toString();

                   // 8. Disconnect the connection
                   conn.disconnect();
