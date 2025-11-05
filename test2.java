<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.io.*,java.net.*,java.util.*,com.fasterxml.jackson.databind.*" %>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.fasterxml.jackson.core.type.TypeReference" %>
<%@ page import="java.util.ArrayList" %>
<head>

<%

    ArrayList aggRefundResultList = new ArrayList();
    ArrayList<Map<String, Object>> refundDetailsList = new ArrayList();
    ArrayList refundDetails = new ArrayList();
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

                out.println("<pre>Transaction: " + transaction + "</pre>");
                refundDetailsList = (ArrayList<Map<String, Object>>) transaction.get("refundDetails");
                    if (refundDetailsList != null && !refundDetailsList.isEmpty()) {
                        aggRefundResultList.addAll(refundDetailsList);
                    }
            }
        }

        out.println("<pre>aggRefundResultList: " + aggRefundResultList + "</pre>");
    } catch (Exception e) {
        out.println("<pre style='color:red;'>Error: " + e + "</pre>");
    }

    try {
        for (int i = 0; i < aggRefundResultList.size(); i++) {
            refundDetails = aggRefundResultList.get(i);
            if( (Map<String, Object>) aggRefundResultList.get(i)) {
                refundDetails = refundDetails.get(i);
            } else {
                refundDetails = 0;
            }
        }
    } catch (Exception e) {
        refundDetails = 0;
        out.println("<pre style='color:red;'>Error: " + e + "</pre>");
    }



%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="bootstrap.min.css" rel="stylesheet"
	type="text/css" />
	<title>Transaction Tracking Details</title>
<link rel="icon" type="image/gif" sizes="16x16" href=""  />
</head>

<style>
body {
		background-image: url(bg.jpg) !important;
        background-size: 100vw 100vh;
		top: 0;
		left: 0;
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
  font-weight:bold;
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
background-color: #000061; color: #fff
}

</style>


<body>
<form name="aggATRNDetails">
		<br /> <br />
		<div class="container">
			<div class="row">
		<% if(flag==1){ %>
				<table class="table table-bordered table-sm" style="background-color: #fff;">
					<thead>
						<tr>
							<th colspan="4" class="head">Transaction Tracking</th>

						</tr>
						<tr>
							<th>Particular</th>
		                    for(int i=0; i< dataList.size(); i++) {
		                        <th>Transaction <% %></th>

		                    }

                                <%if( (ArrayList) dataList.get(i)) ){%>

                                <th>Transaction 2</th>
                                <%} %>

                                <%if(! ( (ArrayList)transTrackDetailList1.get(2) ).get(0).toString().equals("-") ){%>
                                <th>Transaction 3</th>
                                <%} %>

						</tr>
					</thead>
					<tbody>
						<tr>

							<th>Merchant Reference Number</th>
							<td><%=((ArrayList)transTrackDetailList1.get(0)).get(0) == null ? "-":((ArrayList)transTrackDetailList1.get(0)).get(0).toString().trim() %></td>

							<%if(! ( (ArrayList)transTrackDetailList1.get(1) ).get(0).toString().equals("-") ){%>
							<td><%=((ArrayList)transTrackDetailList1.get(1)).get(0) == null ? "-":((ArrayList)transTrackDetailList1.get(1)).get(0).toString().trim()%></td>
							<%} %>

							<%if(! ( (ArrayList)transTrackDetailList1.get(2) ).get(0).toString().equals("-") ){%>
							<td><%=((ArrayList)transTrackDetailList1.get(2)).get(0) == null ? "-":((ArrayList)transTrackDetailList1.get(2)).get(0).toString().trim()%></td>
							<%} %>
						</tr>
						<tr>
							<th>SBIePay Reference Number</th>
							<td><%=((ArrayList)transTrackDetailList1.get(0)).get(1) == null ? "-":((ArrayList)transTrackDetailList1.get(0)).get(1).toString().trim()%></td>

							<%if(! ( (ArrayList)transTrackDetailList1.get(1) ).get(0).toString().equals("-") ){%>
							<td><%=((ArrayList)transTrackDetailList1.get(1)).get(1) == null ? "-":((ArrayList)transTrackDetailList1.get(1)).get(1).toString().trim()%></td>
							<%} %>

							<%if(! ( (ArrayList)transTrackDetailList1.get(2) ).get(0).toString().equals("-") ){%>
							<td><%=((ArrayList)transTrackDetailList1.get(2)).get(1) == null ? "-":((ArrayList)transTrackDetailList1.get(2)).get(1).toString().trim()%></td>
							<%} %>
						</tr>

						<tr>
							<th>Bank Reference Number</th>
							<td><%=((ArrayList)transTrackDetailList1.get(0)).get(2) == null ? "-":((ArrayList)transTrackDetailList1.get(0)).get(2).toString().trim()%></td>

							<%if(! ( (ArrayList)transTrackDetailList1.get(1) ).get(0).toString().equals("-") ){%>
							<td><%=((ArrayList)transTrackDetailList1.get(1)).get(2) == null ? "-":((ArrayList)transTrackDetailList1.get(1)).get(2).toString().trim()%></td>
							<%} %>

							<%if(! ( (ArrayList)transTrackDetailList1.get(2) ).get(0).toString().equals("-") ){%>
							<td><%=((ArrayList)transTrackDetailList1.get(2)).get(2) == null ? "-":((ArrayList)transTrackDetailList1.get(2)).get(2).toString().trim()%></td>
							<%} %>
						</tr>
						<tr>
							<th>Transaction Date & Time</th>
							<td><%=((ArrayList)transTrackDetailList1.get(0)).get(3) == null ? "-":((ArrayList)transTrackDetailList1.get(0)).get(3).toString().trim()%></td>

							<%if(! ( (ArrayList)transTrackDetailList1.get(1) ).get(0).toString().equals("-") ){%>
							<td><%=((ArrayList)transTrackDetailList1.get(1)).get(3) == null ? "-":((ArrayList)transTrackDetailList1.get(1)).get(3).toString().trim()%></td>
							<%} %>

							<%if(! ( (ArrayList)transTrackDetailList1.get(2) ).get(0).toString().equals("-") ){%>
							<td><%=((ArrayList)transTrackDetailList1.get(2)).get(3) == null ? "-":((ArrayList)transTrackDetailList1.get(2)).get(3).toString().trim()%></td>
							<%} %>
						</tr>
						<tr>
							<th>Transaction Amount</th>
							<td><%=((ArrayList)transTrackDetailList1.get(0)).get(4) == null ? "-":((ArrayList)transTrackDetailList1.get(0)).get(4).toString().trim()%></td>

							<%if(! ( (ArrayList)transTrackDetailList1.get(1) ).get(0).toString().equals("-") ){%>
							<td><%=((ArrayList)transTrackDetailList1.get(1)).get(4) == null ? "-":((ArrayList)transTrackDetailList1.get(1)).get(4).toString().trim()%></td>
							<%} %>

							<%if(! ( (ArrayList)transTrackDetailList1.get(2) ).get(0).toString().equals("-") ){%>
							<td><%=((ArrayList)transTrackDetailList1.get(2)).get(4) == null ? "-":((ArrayList)transTrackDetailList1.get(2)).get(4).toString().trim()%></td>
							<%} %>
						</tr>
						<!--<tr>
							<th>Commission Amount</th>
							<td><%=((ArrayList)transTrackDetailList1.get(0)).get(5) == null ? "-":((ArrayList)transTrackDetailList1.get(0)).get(5).toString().trim()%></td>

							<%if(! ( (ArrayList)transTrackDetailList1.get(1) ).get(0).toString().equals("-") ){%>
							<td><%=((ArrayList)transTrackDetailList1.get(1)).get(5) == null ? "-":((ArrayList)transTrackDetailList1.get(1)).get(5).toString().trim()%></td>
							<%} %>


							<%if(! ( (ArrayList)transTrackDetailList1.get(2) ).get(0).toString().equals("-") ){%>
							<td><%=((ArrayList)transTrackDetailList1.get(2)).get(5) == null ? "-":((ArrayList)transTrackDetailList1.get(2)).get(5).toString().trim()%></td>
							<%} %>
						</tr>
						<tr>
							<th>GST</th>
							<td><%=((ArrayList)transTrackDetailList1.get(0)).get(6) == null ? "-":((ArrayList)transTrackDetailList1.get(0)).get(6).toString().trim()%></td>

							<%if(! ( (ArrayList)transTrackDetailList1.get(1) ).get(0).toString().equals("-") ){%>
							<td><%=((ArrayList)transTrackDetailList1.get(1)).get(6) == null ? "-":((ArrayList)transTrackDetailList1.get(1)).get(6).toString().trim()%></td>
							<%} %>

							<%if(! ( (ArrayList)transTrackDetailList1.get(2) ).get(0).toString().equals("-") ){%>
							<td><%=((ArrayList)transTrackDetailList1.get(2)).get(6) == null ? "-":((ArrayList)transTrackDetailList1.get(2)).get(6).toString().trim()%></td>
							<%} %>
						</tr>-->
						<tr>
							<th>Total Amount</th>
							<td><%=((ArrayList)transTrackDetailList1.get(0)).get(5) == null ? "-":((ArrayList)transTrackDetailList1.get(0)).get(5).toString().trim()%></td>

							<%if(! ( (ArrayList)transTrackDetailList1.get(1) ).get(0).toString().equals("-") ){%>
							<td><%=((ArrayList)transTrackDetailList1.get(1)).get(5) == null ? "-":((ArrayList)transTrackDetailList1.get(1)).get(5).toString().trim()%></td>
							<%} %>

							<%if(! ( (ArrayList)transTrackDetailList1.get(2) ).get(0).toString().equals("-") ){%>
							<td><%=((ArrayList)transTrackDetailList1.get(2)).get(5) == null ? "-":((ArrayList)transTrackDetailList1.get(2)).get(5).toString().trim()%></td>
							<%} %>
						</tr>
						<tr>
							<th>Transaction Status</th>
							<td><%=((ArrayList)transTrackDetailList1.get(0)).get(6) == null ? "-":((ArrayList)transTrackDetailList1.get(0)).get(6).toString().trim()%></td>


							<%if(! ( (ArrayList)transTrackDetailList1.get(1) ).get(0).toString().equals("-") ){%>
							<td><%=((ArrayList)transTrackDetailList1.get(1)).get(6) == null ? "-":((ArrayList)transTrackDetailList1.get(1)).get(6).toString().trim()%></td>
							<%} %>


							<%if(! ( (ArrayList)transTrackDetailList1.get(2) ).get(0).toString().equals("-") ){%>
							<td><%=((ArrayList)transTrackDetailList1.get(2)).get(6) == null ? "-":((ArrayList)transTrackDetailList1.get(2)).get(6).toString().trim()%></td>
							<%} %>
						</tr>
						<tr>
							<th>Transaction Status Description</th>
							<td><%=((ArrayList)transTrackDetailList1.get(0)).get(7) == null ? "-":((ArrayList)transTrackDetailList1.get(0)).get(7).toString().trim()%></td>

							<%if(! ( (ArrayList)transTrackDetailList1.get(1) ).get(0).toString().equals("-") ){%>
							<td><%=((ArrayList)transTrackDetailList1.get(1)).get(7) == null ? "-":((ArrayList)transTrackDetailList1.get(1)).get(7).toString().trim()%></td>
							<%} %>


							<%if(! ( (ArrayList)transTrackDetailList1.get(2) ).get(0).toString().equals("-") ){%>
							<td><%=((ArrayList)transTrackDetailList1.get(2)).get(7) == null ? "-":((ArrayList)transTrackDetailList1.get(2)).get(7).toString().trim()%></td>
							<%} %>
						</tr>
						<tr>
							<th>Settlement Date</th>
							<td><%=((ArrayList)transTrackDetailList1.get(0)).get(8) == null ? "-":((ArrayList)transTrackDetailList1.get(0)).get(8).toString().trim()%></td>

							<%if(! ( (ArrayList)transTrackDetailList1.get(1) ).get(0).toString().equals("-") ){%>
							<td><%= ((ArrayList)transTrackDetailList1.get(1)).get(8) == null ? "-":((ArrayList)transTrackDetailList1.get(1)).get(8).toString().trim()%></td>
							<%} %>

							<%if(! ( (ArrayList)transTrackDetailList1.get(2) ).get(0).toString().equals("-") ){%>
							<td><%= ((ArrayList)transTrackDetailList1.get(2)).get(8) == null ? "-":((ArrayList)transTrackDetailList1.get(2)).get(8).toString().trim()%></td>
							<%} %>
						</tr>
					</tbody>

	<%
	    if(flag==1){
		int refundSize =aggRefundResultList1.size();
		int test = 0;

		for(int i=0; max>i; i++){

	%>
					<thead>
						<tr>
							<th colspan="4" class="head">Refund History</th>

						</tr>

					</thead>


		  <tbody>
			<tr>
			  <th>Refund Reference Number</th>
			  <%
			  		int hike = 0;
			  %>

			 <td><%=((ArrayList)((ArrayList)aggRefundResultList1.get(0)).get(test)).get(hike) == null ? "-":((ArrayList)((ArrayList)aggRefundResultList1.get(0)).get(test)).get(hike).toString().trim()%></td>

			 <%if(! ( (ArrayList)((ArrayList)aggRefundResultList1.get(1)).get(test)).get(hike).toString().equals("-") ){%>
			 <td><%=((ArrayList)((ArrayList)aggRefundResultList1.get(1)).get(test)).get(hike) == null ? "-":((ArrayList)((ArrayList)aggRefundResultList1.get(1)).get(test)).get(hike).toString().trim()%></td>
			 <%} %>


			 <%if(! ( (ArrayList)((ArrayList)aggRefundResultList1.get(2)).get(test)).get(hike).toString().equals("-") ){%>
			 <td><%=((ArrayList)((ArrayList)aggRefundResultList1.get(2)).get(test)).get(hike) == null ? "-":((ArrayList)((ArrayList)aggRefundResultList1.get(2)).get(test)).get(hike).toString().trim()%></td>
			 <%} %>
			 <%
			    hike = hike+1;
			 %>

			</tr>
			 <tr>
			  <th>Refund Booking Date & Time</th>
			  <td><%=((ArrayList)((ArrayList)aggRefundResultList1.get(0)).get(test)).get(hike) == null ? "-":((ArrayList)((ArrayList)aggRefundResultList1.get(0)).get(test)).get(hike).toString().trim()%></td>

			  <%if(! ( (ArrayList)((ArrayList)aggRefundResultList1.get(1)).get(test)).get(hike).toString().equals("-") ){%>
			 <td><%=((ArrayList)((ArrayList)aggRefundResultList1.get(1)).get(test)).get(hike) == null ? "-":((ArrayList)((ArrayList)aggRefundResultList1.get(1)).get(test)).get(hike).toString().trim()%></td>
			 <%} %>

			  <%if(! ( (ArrayList)((ArrayList)aggRefundResultList1.get(2)).get(test)).get(hike).toString().equals("-") ){%>
			 <td><%=((ArrayList)((ArrayList)aggRefundResultList1.get(2)).get(test)).get(hike) == null ? "-":((ArrayList)((ArrayList)aggRefundResultList1.get(2)).get(test)).get(hike).toString().trim()%></td>
			 <%} %>
			</tr>
			<%
			    hike = hike+1;
			 %>

			<tr>
			 <th>Refund Type</th>
			  <td><%=((ArrayList)((ArrayList)aggRefundResultList1.get(0)).get(test)).get(hike) == null ? "-":((ArrayList)((ArrayList)aggRefundResultList1.get(0)).get(test)).get(hike).toString().trim()%></td>


				<%if(! ( (ArrayList)((ArrayList)aggRefundResultList1.get(1)).get(test)).get(hike).toString().equals("-") ){%>
			 <td><%=((ArrayList)((ArrayList)aggRefundResultList1.get(1)).get(test)).get(hike) == null ? "-":((ArrayList)((ArrayList)aggRefundResultList1.get(1)).get(test)).get(hike).toString().trim()%></td>
				 <%} %>


			<%if(! ( (ArrayList)((ArrayList)aggRefundResultList1.get(2)).get(test)).get(hike).toString().equals("-") ){%>
			 <td><%=((ArrayList)((ArrayList)aggRefundResultList1.get(2)).get(test)).get(hike) == null ? "-":((ArrayList)((ArrayList)aggRefundResultList1.get(2)).get(test)).get(hike).toString().trim()%></td>
			 <%} %>
			</tr>
			<%
			    hike = hike+1;
			 %>
			 <tr>
			  <th>Refund Amount</th>
			   <td><%=((ArrayList)((ArrayList)aggRefundResultList1.get(0)).get(test)).get(hike) == null ? "-":((ArrayList)((ArrayList)aggRefundResultList1.get(0)).get(test)).get(hike).toString().trim()%></td>

			<%if(! ( (ArrayList)((ArrayList)aggRefundResultList1.get(1)).get(test)).get(hike).toString().equals("-") ){%>
			 <td><%=((ArrayList)((ArrayList)aggRefundResultList1.get(1)).get(test)).get(hike) == null ? "-":((ArrayList)((ArrayList)aggRefundResultList1.get(1)).get(test)).get(hike).toString().trim()%></td>
			  <%} %>


			 <%if(! ( (ArrayList)((ArrayList)aggRefundResultList1.get(2)).get(test)).get(hike).toString().equals("-") ){%>
			 <td><%=((ArrayList)((ArrayList)aggRefundResultList1.get(2)).get(test)).get(hike) == null ? "-":((ArrayList)((ArrayList)aggRefundResultList1.get(2)).get(test)).get(hike).toString().trim()%></td>
			  <%} %>
			 </tr>
			 <%
			    hike = hike+1;
			 %>
			 <tr>
			  <th>Refund Status</th>
			   <td><%=((ArrayList)((ArrayList)aggRefundResultList1.get(0)).get(test)).get(hike) == null ? "-":((ArrayList)((ArrayList)aggRefundResultList1.get(0)).get(test)).get(hike).toString().trim()%></td>


			<%if(! ( (ArrayList)((ArrayList)aggRefundResultList1.get(1)).get(test)).get(hike).toString().equals("-")){%>
			 <td><%=((ArrayList)((ArrayList)aggRefundResultList1.get(1)).get(test)).get(hike) == null ? "-":((ArrayList)((ArrayList)aggRefundResultList1.get(1)).get(test)).get(hike).toString().trim()%></td>
			  <%} %>


			 <%if(! ( (ArrayList)((ArrayList)aggRefundResultList1.get(2)).get(test)).get(hike).toString().equals("-") ){%>
			 <td><%=((ArrayList)((ArrayList)aggRefundResultList1.get(2)).get(test)).get(hike) == null ? "-":((ArrayList)((ArrayList)aggRefundResultList1.get(2)).get(test)).get(hike).toString().trim()%></td>
			 <%} %>
			</tr>

			<%
			    hike = hike+1;
			 %>
			<tr>
			  <th>Refund Processed Date & Time</th>
			  <td><%=((ArrayList)((ArrayList)aggRefundResultList1.get(0)).get(test)).get(hike) == null ? "-":((ArrayList)((ArrayList)aggRefundResultList1.get(0)).get(test)).get(hike).toString().trim()%></td>


			 <%if(! ( (ArrayList)((ArrayList)aggRefundResultList1.get(1)).get(test)).get(hike).toString().equals("-")){%>
			 <td><%=((ArrayList)((ArrayList)aggRefundResultList1.get(1)).get(test)).get(hike) == null ? "-":((ArrayList)((ArrayList)aggRefundResultList1.get(1)).get(test)).get(hike).toString().trim()%></td>
			 <%} %>


			<%if(! ( (ArrayList)((ArrayList)aggRefundResultList1.get(2)).get(test)).get(hike).toString().equals("-")){%>
			 <td><%=((ArrayList)((ArrayList)aggRefundResultList1.get(2)).get(test)).get(hike) == null ? "-":((ArrayList)((ArrayList)aggRefundResultList1.get(2)).get(test)).get(hike).toString().trim()%></td>
			 <%} %>
			</tr>
		  </tbody>
  <%
			 	test= test+1;

			}
		}else{
			flag = 0;
				%>
				<table id="msgtable" width="80%" border="0" cellspacing="1" cellpadding="2"  align="center" class="fonttd">
						<tr >
						<td align="center" bgcolor="#FFFFFF" >No Refund History Found</td>
						</tr>
				</table>
			 <%
			}

%>
			</table>
	<%}else{
		flag = 0;
			%>
			<!--<div id="msgtable" class="container my-4 ">
					 <p class="headingRecord" >No Records Found</p>
					 <hr>
			</div>-->
		 <%
			session.setAttribute("trackValList", transactionDetails);
			session.setAttribute("noRecords", "noRecords");
			String url= "";
			String secuUrl=url.concat("secure/transactionTrack");
			response.sendRedirect(secuUrl);

		}
		 %>
		 	</div>
		</div>


          <div class="text-center" style=" margin-top: 100px; ">
            <button type="button" class="btn btn-primary btn-lg btnBack"  onclick="window.location.href='/secure/transactionTrack'">Back</button>
            <button type="button" class="btn btn-secondary btn-lg btnHome" onclick="window.location.href='/secure/home'">Home</button>

            <!--  UAT Link
             <button type="button" class="btn btn-primary btn-lg" style="margin-right: 100px"  onclick="window.location.href='/secure/transactionTrack'">Back</button>
            <button type="button" class="btn btn-secondary btn-lg"  style="margin-left: 100px"  onclick="window.location.href='/secure/home'">Home</button> -->
          </div>
	</form>



</body>
</html>
