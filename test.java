<jsp:directive.include file="../secure/merchant/aggxssCheck.jsp" />
<%-- <jsp:directive.include file="aggSbiCommonInclude.jsp" /> --%>
<jsp:useBean id="transactionTrackCtlr" scope="session"
	class="com.sbi.payagg.uilogic.TracsactionTrackController" />
<%@ page
	import=" com.sbi.payagg.uilogic.*, com.sbi.payagg.util.*, com.sbi.payagg.util.dataaccess.*, java.util.*,java.security.KeyPair"%>
	<%@ page import="nl.captcha.Captcha"%>
<html>
<head>

<%

response.addHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires",0);
response.setHeader("Cache-Control", "no-store"); 
response.setHeader("X-Frame-Options", "SAMEORIGIN");
	

ArrayList aggRefundResultList1 = new ArrayList();
ArrayList aggRefundResultList3 = new ArrayList();
ArrayList aggRefundResultList = new ArrayList();
ArrayList formValidationValues = new ArrayList();

	
	String merchantRefValue ="", sbiepayRefValue ="", bankRefValue ="", transDateRefValue="", cardPay="", ref="" ,paymentModeValue="", merchantId="",gatewayValue="",payprocid="";
	String marRefNo="", atrn="", bankRefNo ="", transDate ="", transAmount ="", commisionAmount ="", gst ="", totalAmount ="", transStatus="", transStatusDesc="", settlementDate ="";   
	String arrn="", sbiepayRefNum="", bankRefNum="", captchaValue="" ; 
	int max=0;
	
	ArrayList transTrackGetList = new ArrayList();
	ArrayList transTrackDetailList = new ArrayList();
	ArrayList transTrackDetailList1 = new ArrayList();
	int size = 0;
	int listSize = 0;
	int flag = 0;
	String transactionTrackingUrl="/secure/transactionTrack";
	 merchantRefValue = request.getParameter("merchantRefValue")==null ? "" : request.getParameter("merchantRefValue");
	 GenericExceptionLog.log("encrypt merchantRefValue " + merchantRefValue);
	
	 sbiepayRefValue = request.getParameter("sbiepayRefValue")==null ? "" : request.getParameter("sbiepayRefValue");
	 GenericExceptionLog.log("encrypt sbiepayRefValue: " + sbiepayRefValue);
	
	 bankRefValue = request.getParameter("bankRefValue")==null ? "" : request.getParameter("bankRefValue");
	 GenericExceptionLog.log("encrypt bankRefValue: " + bankRefValue);
	
	 transDateRefValue = request.getParameter("transDateRefValue")==null ? "" : request.getParameter("transDateRefValue");
	 GenericExceptionLog.log("encrypt transDateRefValue: " + transDateRefValue);
	
	 merchantId = request.getParameter("merchantIdValue")==null ? "" : request.getParameter("merchantIdValue");
	 GenericExceptionLog.log("encrypt merchantId: " + merchantId);
	
	 paymentModeValue = request.getParameter("paymentModeValue")==null ? "" : request.getParameter("paymentModeValue");
	 GenericExceptionLog.log("encrypt paymentModeValue: " + paymentModeValue);
	 
	 captchaValue = request.getParameter("captchaValue")==null ? "" : request.getParameter("captchaValue");
	 GenericExceptionLog.log("encrypt captchaValue: " + captchaValue);
	 
	 cardPay = request.getParameter("cardTypeValue")==null?"":request.getParameter("cardTypeValue");
	 GenericExceptionLog.log("encrypt cardPay: " + cardPay);
	 
	 gatewayValue = request.getParameter("gatewayValue")==null ? "" : request.getParameter("gatewayValue");
	 GenericExceptionLog.log("encrypt gatewayValue: " + gatewayValue);
	 
	 captchaValue = request.getParameter("captchaValue")==null ? "" : request.getParameter("captchaValue");
	 GenericExceptionLog.log("encrypt captchaValue: " + captchaValue);
	 
	 ref =  request.getParameter("ref")==null?"":request.getParameter("ref");
	 GenericExceptionLog.log("encrypt ref: " + ref);
	
	 String merRefVal = request.getParameter("merchantRefValue");
	KeyPair keys = (KeyPair) request.getSession(false).getAttribute("keys");
	if(merRefVal!=null &&  keys!=null){
	 merchantRefValue    = AggGenericUtil.setValue(JCryptionUtil.decrypt(request.getParameter("merchantRefValue"), keys).trim());
	 GenericExceptionLog.log("decrypt merchantRefValue=" + merchantRefValue);
	 formValidationValues.add(merchantRefValue);
	
	 sbiepayRefValue     = AggGenericUtil.setValue(JCryptionUtil.decrypt(request.getParameter("sbiepayRefValue"), keys).trim());
	 GenericExceptionLog.log("decrypt sbiepayRefValue=" + sbiepayRefValue);
	 formValidationValues.add(sbiepayRefValue);
	
	 bankRefValue        = AggGenericUtil.setValue(JCryptionUtil.decrypt(request.getParameter("bankRefValue"), keys).trim());
	 GenericExceptionLog.log("decrypt bankRefValue=" + bankRefValue);
	 formValidationValues.add(bankRefValue);
	 
	 transDateRefValue   = AggGenericUtil.setValue(JCryptionUtil.decrypt(request.getParameter("transDateRefValue"), keys).trim());
	 GenericExceptionLog.log("decrypt transDateRefValue=" + transDateRefValue);
	 formValidationValues.add(transDateRefValue);
	
	 merchantId          = AggGenericUtil.setValue(JCryptionUtil.decrypt(request.getParameter("merchantIdValue"), keys).trim());
	 GenericExceptionLog.log("decrypt merchantId=" + merchantId);
	 formValidationValues.add(merchantId);
	 
	 paymentModeValue             = AggGenericUtil.setValue(JCryptionUtil.decrypt(request.getParameter("paymentModeValue"), keys).trim());
	 GenericExceptionLog.log("decrypt paymentModeValue=" + paymentModeValue);
	 formValidationValues.add(paymentModeValue);
	
	 captchaValue             = AggGenericUtil.setValue(JCryptionUtil.decrypt(request.getParameter("captchaValue"), keys).trim());
	 GenericExceptionLog.log("decrypt captchaValue=" + captchaValue);
	 formValidationValues.add(captchaValue);
	 
	 cardPay =  AggGenericUtil.setValue(JCryptionUtil.decrypt(request.getParameter("cardTypeValue"), keys).trim());
	 GenericExceptionLog.log("decrypt cardPay=" + cardPay);
	 formValidationValues.add(cardPay);
	
	 gatewayValue =  AggGenericUtil.setValue(JCryptionUtil.decrypt(request.getParameter("gatewayValue"), keys).trim());
	 GenericExceptionLog.log("decrypt gatewayValue=" + gatewayValue);
	 formValidationValues.add(gatewayValue);
	}
	 else{
		 //String coperateUrl = FetchProperties.getValue("CoprateHomeUrl");
			//response.sendRedirect(coperateUrl);
		   String newURL=response.encodeRedirectURL("/secure/aggErrorPage.jsp");
			response.sendRedirect(newURL);
			return;
	 }
	 
	 
	 //added by govinda
	 Captcha captchaAnswer = (Captcha)session.getAttribute(Captcha.NAME);
	 
	 if(captchaValue!=null && captchaValue!="") {
			if(captchaAnswer!= null && captchaAnswer.isCorrect(captchaValue))
			{
				GenericExceptionLog.log("Captcha Validate in servlet  ", "AggMerchantUserLoginAction");
			}
			else
			{
				GenericExceptionLog.log("Captcha unmatched  ", "AggMerchantUserLoginAction");
				response.sendRedirect(transactionTrackingUrl + "?msg=e512go");
				return;
			}
		}
	 
	 boolean validationCheck=false;
	 /*  try
	 {
	 int paraCount=0;
	 for(Enumeration tempXss_e = request.getParameterNames(); tempXss_e.hasMoreElements(); )
	 {
	     String a = (String)tempXss_e.nextElement();
	     paraCount++;
	 }
	 int i=0;
	 String[][] xss_e=new String[paraCount][2];
	 for(Enumeration tempXss_e = request.getParameterNames(); tempXss_e.hasMoreElements(); )
	 {
	     xss_e[i][0] = (String)tempXss_e.nextElement();
	     xss_e[i][1] = request.getParameter(xss_e[i][0]);
	     xss_e[i][1] = xss_e[i][1].toLowerCase();
	     i++;
	 }
	 String xss_strQString=null;
	 Properties props = new Properties();
	 String propValue=null;
	 String propName = "excludeParaLi";
	 String excludeList = FetchProperties.getValue("agg_EXCLUDE_LIST");

	 String[] excludeParaList = null;

	 try{
	 	FileInputStream fis = new FileInputStream(excludeList);
	 	props.load(fis);
	 	if(props.getProperty(propName) != null){
	 		propValue=props.getProperty(propName);
	 	}
	 }
	 catch(Exception t)
	 {
	 	GenericExceptionLog.exceptionJsp(t, "aggERupeeEgMobileTxnPaymentDetails.jsp","aggERupeeEgMobileTxnPaymentDetails");
	 }
	 	excludeParaList=propValue.split(",");
	 validationCheck = AggTransactionValidation.aggXssCheckValidation(xss_e, excludeParaList);
	 }catch(Exception e)
	 {
	 	GenericExceptionLog.exceptionJsp(e, "aggERupeeEgMobileTxnPaymentDetails.jsp","aggERupeeEgMobileTxnPaymentDetails");

	 }*/
	 validationCheck = AggTransactionValidation.formValidation(formValidationValues);
	 
	 if(validationCheck){
	
	 ArrayList<String> transactionDetails = new ArrayList<String>();
	
	 transactionDetails.add(merchantRefValue);
	transactionDetails.add(sbiepayRefValue);
	transactionDetails.add(bankRefValue);
	transactionDetails.add(transDateRefValue);
	transactionDetails.add(merchantId);
	transactionDetails.add(paymentModeValue);
	transactionDetails.add(ref);
	transactionDetails.add(gatewayValue);
	payprocid=cardPay;
	transactionDetails.add(payprocid);
	
	GenericExceptionLog.log("transactionDetails  txntrckaction.jsp.......: " + transactionDetails);
	
	try
	{
		 
		
		transTrackGetList = transactionTrackCtlr.aggMarchantDetailByMarchantRef(transactionDetails);
		GenericExceptionLog.log("transTrackGetList  txntrckaction.jsp.......: " + transTrackGetList);
		listSize = transTrackGetList.size();
		
		
		if(listSize>0 ){
			flag = 1;	
		
		for(int i=0; listSize>i; i++){
		
		transTrackDetailList =(ArrayList) transTrackGetList.get(i);
		transTrackDetailList1.add(transTrackDetailList);
		}
		size = transTrackDetailList1.size();
		
       	
    	 for(int j=0; j<3-size; j++){
    		  ArrayList transTrackDetailList2 = new ArrayList();
       	   
    		  for(int k=0; ((ArrayList)transTrackGetList.get(0)).size()>=k; k++){  
   			    transTrackDetailList2.add('-');
      		 }
			transTrackDetailList1.add(transTrackDetailList2);
		}
		}
		}
	
	catch(Exception t)
	{
		GenericExceptionLog.exceptionJsp(t, "transactionTrack.jsp", "transactionTrack");
	}
	
	if(listSize>0){
	
	//ARRN Retrival

	//start of refund logic  listSize
	for(int n=0; transTrackDetailList1.size()>n; n++){
		
		
		atrn = ((ArrayList)transTrackDetailList1.get(n)).get(1) == null ? "-":((ArrayList)transTrackDetailList1.get(n)).get(1).toString().trim();
		
		ArrayList arrnList = null;
		arrnList = transactionTrackCtlr.getARRNList(atrn);
		
		String arrnStrList = arrnList.toString();
		
		arrnStrList =arrnStrList
		.replace("[[", "'")
		.replace("]]", "'")
		.replace("]", "'")
		.replace("[", "'")
		.trim();

		
	//Refund data
	
	
	String refundRefNo = "", refundBookingDate="", refundType="", refundAmount="", refundCode="", refundDiscr="", refundProcessDate="";
	
	String aggregratorId ="SBIEPAY";
	
	if(arrn !=null)
	{
		
				
	try
	{	
		
		//Share in string of arrn in this method (arrnstring,aggregratorId);
		aggRefundResultList=transactionTrackCtlr.getTransTrackRefundList(arrnStrList,aggregratorId);
		
		GenericExceptionLog.log("aggRefundResultList transactionTrackAction.jsp....."+aggRefundResultList);
		 
		//add this in main list
		aggRefundResultList1.add(aggRefundResultList);
		
		
		if(max<aggRefundResultList.size()){
			max = aggRefundResultList.size();
			
		}
		
	}
		catch(Exception t)
		{
			
			GenericExceptionLog.exceptionJsp(t, "transactionTrack.jsp", "transactionTrackRefundList");
		}
	}
	
	}
	}else{
		flag = 0;
		}

	
	//To add - in null 

	
	for(int m=0; m<aggRefundResultList1.size(); m++){
		
		ArrayList templist = ((ArrayList) aggRefundResultList1.get(m)); 
	
			for(int x=0;x<max;x++)
			{
				
				if(x>=templist.size()){
					
					
				templist.add(new ArrayList(Arrays.asList("-", "-", "-", "-", "-", "-", "-", "-")));
				
				}
			}
			aggRefundResultList1.set(m, templist);
			
	}	
	
	
%>



<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="stylesheet/bootstrap.min.css" rel="stylesheet"
	type="text/css" />
<title>Transaction Tracking Details</title>
<link rel="icon" type="image/gif" sizes="16x16" href="<%=FetchProperties.getJVMProperty("SECURE") +"secure/img/favicon-sbiepay.ico"%>"  />
</head>
<style>
body{
		background-image: url(/secure/images/bg.jpg) !important;
        background-size: 100vw 100vh;
		top: 0;
		left: 0;
		height: 100vh;
		width: 100vw;}
.head {
	background-color: #000061;
	color: #fff;
	text-align: center;
}
.headingRecord{
  color: #000;
  font-weight:bold;
  font-size: 25px;
  text-align: center;
  margin: 0 30px;
  }
.btnHome{
background: #E0E0E0;
margin-left: 100px;
}
.btnBack{
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
							<th>Transaction 1</th>
							
							<%if(! ( (ArrayList)transTrackDetailList1.get(1) ).get(0).toString().equals("-") ){%>
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

						<tr>
							<th>CIN Number</th>
							<td><%=((ArrayList)transTrackDetailList1.get(0)).get(9) == null ? "-":((ArrayList)transTrackDetailList1.get(0)).get(9).toString().trim()%></td>

							<%if(! ( (ArrayList)transTrackDetailList1.get(1) ).get(0).toString().equals("-") ){%>
							<td><%= ((ArrayList)transTrackDetailList1.get(1)).get(9) == null ? "-":((ArrayList)transTrackDetailList1.get(1)).get(9).toString().trim()%></td>
							<%} %>
							
							<%if(! ( (ArrayList)transTrackDetailList1.get(2) ).get(0).toString().equals("-") ){%>
							<td><%= ((ArrayList)transTrackDetailList1.get(2)).get(9) == null ? "-":((ArrayList)transTrackDetailList1.get(2)).get(9).toString().trim()%></td>
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
			String url= FetchProperties.getJVMProperty("SECURE");
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
	<%}else
	{
		String url= FetchProperties.getJVMProperty("SECURE");
		String secuUrl=url.concat("secure/aggxssErrorPage.jsp");
		response.sendRedirect(secuUrl);
	}%>

</body>
</html>
