<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Transaction Tracking Details</title>
    <link href="/secure/stylesheet/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link rel="icon" type="image/gif" sizes="16x16" href="/secure/img/favicon-sbiepay.ico" />

    <style>
        body {
            background-image: url(/secure/images/bg.jpg) !important;
            background-size: 100vw 100vh;
            height: 100vh;
            width: 100vw;
        }
        .head {
            background-color: #000061;
            color: #fff;
            text-align: center;
        }
        .headingRecord {
            color: #000;
            font-weight: bold;
            font-size: 25px;
            text-align: center;
            margin: 0 30px;
        }
        .btnHome {
            background: #E0E0E0;
            margin-left: 100px;
        }
        .btnBack {
            margin-right: 100px;
            background-color: #000061;
            color: #fff;
        }
        #noData {
            text-align: center;
            font-size: 20px;
            color: red;
            margin-top: 50px;
        }
    </style>
</head>
<body>
    <div class="container mt-4">
        <h3 class="text-center">Transaction Tracking</h3>
        <div id="noData" style="display:none;">No Records Found</div>
        <table id="txnTable" class="table table-bordered table-sm mt-3" style="display:none;background-color:#fff;">
            <thead>
                <tr><th colspan="4" class="head">Transaction Tracking</th></tr>
                <tr>
                    <th>Particular</th>
                    <th>Transaction 1</th>
                    <th>Transaction 2</th>
                    <th>Transaction 3</th>
                </tr>
            </thead>
            <tbody id="txnBody"></tbody>
        </table>
    </div>

    <script>
        // --- API CALL HERE ---
        // Replace this with your actual endpoint
        const apiUrl = "/api/transaction/track";

        // Example: You can collect these from query params or session
        const params = {
            merchantRefValue: "123456",
            sbiepayRefValue: "EPAY9876",
            bankRefValue: "BNK123",
            transDateRefValue: "2025-11-03"
        };

        // Fetch data from API
        fetch(apiUrl, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(params)
        })
        .then(response => {
            if (!response.ok) throw new Error("API error");
            return response.json();
        })
        .then(data => {
            // Expecting structure like:
            // { flag: 1, transactions: [ { merchantRef, sbiepayRef, bankRef, dateTime, amount, status, desc }, ... ] }
            if (data.flag === 1 && data.transactions && data.transactions.length > 0) {
                renderTable(data.transactions);
            } else {
                document.getElementById("noData").style.display = "block";
            }
        })
        .catch(err => {
            console.error("Error:", err);
            document.getElementById("noData").style.display = "block";
        });

        // --- RENDER FUNCTION ---
        function renderTable(transactions) {
            document.getElementById("txnTable").style.display = "table";

            const fields = [
                "Merchant Reference Number",
                "SBIePay Reference Number",
                "Bank Reference Number",
                "Transaction Date & Time",
                "Transaction Amount",
                "Total Amount",
                "Transaction Status",
                "Transaction Status Description"
            ];

            const body = document.getElementById("txnBody");
            body.innerHTML = "";

            // Fill rows dynamically
            fields.forEach(field => {
                const row = document.createElement("tr");
                const th = document.createElement("th");
                th.innerText = field;
                row.appendChild(th);

                // For each transaction
                for (let i = 0; i < 3; i++) {
                    const td = document.createElement("td");
                    if (transactions[i]) {
                        switch (field) {
                            case "Merchant Reference Number": td.innerText = transactions[i].merchantRef || "-"; break;
                            case "SBIePay Reference Number": td.innerText = transactions[i].sbiepayRef || "-"; break;
                            case "Bank Reference Number": td.innerText = transactions[i].bankRef || "-"; break;
                            case "Transaction Date & Time": td.innerText = transactions[i].dateTime || "-"; break;
                            case "Transaction Amount": td.innerText = transactions[i].amount || "-"; break;
                            case "Total Amount": td.innerText = transactions[i].totalAmount || "-"; break;
                            case "Transaction Status": td.innerText = transactions[i].status || "-"; break;
                            case "Transaction Status Description": td.innerText = transactions[i].desc || "-"; break;
                            default: td.innerText = "-";
                        }
                    } else {
                        td.innerText = "-";
                    }
                    row.appendChild(td);
                }
                body.appendChild(row);
            });
        }
    </script>
</body>
</html>
