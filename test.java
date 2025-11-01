<%@page
	import="java.util.*, java.text.*, java.io.*,com.sbi.payagg.util.FetchProperties,com.sbi.payagg.uilogic.*,com.sbi.payagg.util.*"%>
	
<jsp:directive.include file="../secure/merchant/aggxssCheck.jsp" />
<jsp:directive.include file="../secure/admin/aggUrlCheck.jsp" />

<jsp:useBean id="transactionTrackCtlr" scope="session" class="com.sbi.payagg.uilogic.TracsactionTrackController" />

<%@page import="java.sql.*"%>


<%
//domain migration
String baseURL=request.getScheme()+"://"+request.getServerName();
System.out.println("baseURL Epay-------------------------- : "+baseURL);



//String xmlName = FetchProperties.getJVMProperty("SECURE");
String xmlName = baseURL;//domain migration
//String captchaLink = FetchProperties.getJVMProperty("SECURE")+"secure/agg_captcha_MIF.jsp";
String captchaLink = baseURL+"secure/agg_captcha_MIF.jsp";//domain migration

String msg =  request.getParameter("msg") == null ? "" : request.getParameter("msg");
String merchantRef = request.getParameter("merchantRef") == null ? "" : request.getParameter("merchantRef");
String sbiepayRef = request.getParameter("sbiepayRef") == null ? "" : request.getParameter("sbiepayRef");
String bankRef = request.getParameter("bankRef") == null ? "" : request.getParameter("bankRef");
String txnDateRef = request.getParameter("txnDateRef") == null ? "" : request.getParameter("txnDateRef");
String merchantId = request.getParameter("merchantId") == null ? "" : request.getParameter("merchantId");
String paymentMode = request.getParameter("paymentMode") == null ? "" : request.getParameter("paymentMode");
String gateway = request.getParameter("gateway") == null ? "" : request.getParameter("gateway");
String cardType = request.getParameter("cardType") == null ? "" : request.getParameter("cardType");


if(msg.equalsIgnoreCase("e512go"))			
	msg=FetchProperties.getPropertyValue("AGG_LOGIN_CAPTCHA_INVALID");

try {
	//String adminURL = FetchProperties.getJVMProperty("SECURE");
		String adminURL = baseURL;//domain migration
	String sourceURL = request.getRequestURL().toString();
	if (sourceURL != null && !sourceURL.equals("") && !sourceURL.contains(adminURL)) {	
		
		response.sendRedirect(baseURL+ "secure/aggErrorPage.jsp");//domain migration
		//response.sendRedirect(FetchProperties.getJVMProperty("SECURE") + "secure/aggErrorPage.jsp");
		return;
	}
} catch (Exception e) {
	GenericExceptionLog.exceptionJava(e, "Exception Occured at redirection()", "transactionTrack");
}

GenericExceptionLog.log("captchaLink....... " + captchaLink);
//*********************Get Counter for Captcha******************

String strCounter  = (String)session.getAttribute("counter");
int counter = 0;
if(strCounter == null || strCounter.equals(""))
{
  session.setAttribute("counter", "0");
}
else
{
  counter = Integer.parseInt(strCounter);
}


response.addHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires",0);
response.setHeader( "Set-Cookie", "name=value; HttpOnly");
if (response.containsHeader( "SET-COOKIE" )) {
  response.setHeader( "SET-COOKIE", "Path=/secure/admin; Secure; HttpOnly" );
}
	
	String sessionVal = session.getAttribute("abc") == null ? "" : session.getAttribute("abc").toString();

	
    String sbiRef = "" , merRef= "", merchId ="", txnDate="", payMode = "";
	

	if("abc".equalsIgnoreCase(sessionVal)){

		ArrayList validationsParam=new ArrayList<>();

		validationsParam 	=  (ArrayList) (session.getAttribute("trackValList") == null ? "" : session.getAttribute("trackValList"));

		merRef	=	validationsParam.get(0)	== null ? "" : validationsParam.get(0).toString();
		sbiRef	=	validationsParam.get(1)	== null ? "" : validationsParam.get(1).toString();
		txnDate =	validationsParam.get(3)	== null ? "" : validationsParam.get(3).toString();
		merchId =	validationsParam.get(4)	== null ? "" : validationsParam.get(4).toString();
		payMode =	validationsParam.get(5)	== null ? "" : validationsParam.get(5).toString();

		%>
		 <script>
		     $("msgtable").show();
		 </script>
		<%

		session.removeAttribute("trackValList");
		session.removeAttribute("abc");
		
	}

%>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="cache-control" content="no-cache">

<script type="text/javascript" src="scripts/transactionTrack.js"></script>
<script type="text/javascript" src="<%= FetchProperties.getValue("jqueryMinfiedFile") %>"></script>
<script type="text/javascript" src="scripts/jquery-ui.js"></script>
<script type="text/javascript" src="scripts/jquery-ui.min.js"></script>
<script type="text/javascript" src="scripts/bootstrap.min.js"></script>
<script type="text/javascript" src="scripts/fontawesome.js"></script>

<!--<script type="text/javascript" src="<%=FetchProperties.getJVMProperty("SECURE") + "secure/merchant/scripts/assets/js/security/jquery.jcryption-1.1.min.js"%>"></script>-->

<script type="text/javascript" src="<%=FetchProperties.getJVMProperty("SECURE") + "secure/merchant/scripts/assets/js/security/jquery.jcryption-1.1.min.js"%>"></script>


<!--<script type="text/javascript" src="<%=FetchProperties.getJVMProperty("SECURE") + "secure/merchant/scripts/assets/js/security/jquery.jcryption-1.1.min.js"%>"></script>-->


<script type="text/javascript" src="<%=baseURL + "secure/merchant/scripts/assets/js/security/jquery.jcryption-1.1.min.js"%>"></script>



<!--link rel='icon' href=<%=FetchProperties.getJVMProperty("SECURE")+FetchProperties.getValue("iconImage") %> type='image/x-icon' />-->

<link rel='icon' href=<%=baseURL+FetchProperties.getValue("iconImage") %> type='image/x-icon' /> <!--//domain migration-->


<!--<link rel='shortcut icon' href=<%=FetchProperties.getJVMProperty("SECURE")+FetchProperties.getValue("iconImage") %> type='image/xicon' />-->

<link rel='shortcut icon' href=<%=baseURL+FetchProperties.getValue("iconImage") %> type='image/xicon' />


<!-- <link rel="stylesheet" href="../../stylesheet/font-awesome.min.css" />  -->
<link rel="stylesheet" href="../../stylesheet/jquery-ui.css" />
<link href="stylesheet/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="stylesheet/transactionTrack.css" rel="stylesheet" type="text/css" />
<title>Transaction Tracking</title>
<style>

@media only screen and (max-width: 600px) {

	.tabs > label {
		display: block;
	}
}

</style>
</head>
<body>

	 		<form action="transactionTrackAction" name="formTransaction" id="formTrans"   method="post" >
	
<div class="container track">	
	<div class="row">	
			<img src="images/sbi_epay_logo.jpg" style="width:auto">     
		 
		<div style="text-align: center;">  
			<h3><b>Transaction Tracking</b></h3>		
		</div>
  
				<div class="col-md-12">
	
 
				<div class="tabs">
				<span><b>Search By:</b></span>
				  <label>
				  	<input type="radio" class="radio-inline" name="radios" value="merchantRefDiv" 
				  				onclick="openSearch(event, 'merchantRefDiv')" id="marchantRefBtn" name="marchantRefBtn" checked>
				  				
					<span class="outside"><span class="inside"></span></span>Merchant Ref No.</label>
				 <!-- <label>
				  	<input type="radio" class="radio-inline" name="radios" value="sbiepayRefDiv" 
				  				onclick="openSearch(event, 'sbiepayRefDiv')" id="sbiepayRefBtn" name="sbiepayRefBtn"    >
					<span class="outside"><span class="inside"></span></span>SBIePay Ref No.</label>
				  <label>
				  <input type="radio" class="radio-inline" name="radios" value="bankRefDiv"
				  				 onclick="openSearch(event, 'bankRefDiv')" id="bankRefBtn" name="bankRefBtn"  >
										<span class="outside"><span class="inside"></span></span>Bank Ref No.</label>-->
				 </div>
					<div class="form-group row " id="merchantRefDiv"
						style="margin-bottom: 0px; margin-bottom: 15px;">
						<label id="merchantRefLabel" class="col-sm-12 col-md-4 col-lg-4 col-form-label" 
						   style="margin-bottom: 21px; margin-top: 5px;">Merchant Ref Number</label>
						<div class="col-sm-12 col-md-7 col-lg-7">
							<input type="text" class="form-control" id="merchantRef" name="merchantRef" value="<%=merRef%>" />
							 <span id="merValMsg" class="clr"></span>
						</div>
						<div id="div2"></div>
					</div>

					<div class="form-group row " id="sbiepayRefDiv"
						style="margin-bottom: 0px; margin-bottom: 15px;">

						<label id="sbiepayRefLabel" class="col-sm-12 col-md-4 col-lg-4 col-form-label"
							style="margin-bottom: 21px; margin-top: 5px;">SBIePay Ref Number </label>

						<div class="col-sm-12 col-md-7 col-lg-7">
							<input type="text" class="form-control" id="sbiepayRef" name="sbiepayRef" value="<%=sbiRef%>" /> 
							<span id="sbiepayMsg" class="clr"></span>
						</div>
						<div id="div2"></div>
					</div>

					<div class="form-group row tabcontent"
						style="display: none; margin-bottom: 15px;" id="bankRefDiv">

						<label id="bankRefLabel" class="col-sm-8 col-md-4 col-lg-4 col-form-label "
							style="margin-bottom: 21px; margin-top: 5px;">Bank Ref Number </label>

						<div class="col-sm-12 col-md-7 col-lg-7">
							<input type="text" class="form-control" id="bankRef" name="bankRef" value="<%=bankRef%>"  />
							 <span id="bankRefMsg" class="clr"></span>
						</div>
						<div id="div2"></div>
					</div>

						<div class="form-group row " style="margin-bottom: 15px;">
						
							<label class="col-sm-12 col-md-4 col-lg-4 col-form-label label-mandatory" style="margin-bottom: 21px; margin-top: 5px;">Transaction Date</label>
						
						<div class="col-sm-12 col-md-7 col-lg-7">
							<div class="form-group">
								<div class="input-group">
									<input type="text" class="form-control" id="txnDateRef" name="txnDateRef" value = "<%=txnDate%>" onkeydown="return false " /> 
								
									 <label class="input-group-addon btn" for="txnDateRef" > 
										  <a class="ui-datepicker-trigger" id="calender" ><img  src="../images/calendar.gif"  alt="..." width="20px" height="20px"></img></a> 
									</label>								
								</div>
										<span id="transDateRefMsg" class="clr"></span>
							</div>
						</div>
					</div> 
					
					<div class="form-group row" style="margin-bottom: 15px;" id="merchantIdDiv">
					
							<label class="col-sm-8 col-md-4 col-lg-4 col-form-label" style=" margin-bottom: 21px; margin-top: 5px; ">Merchant Id </label>
						
						<div class="col-sm-12 col-md-7 col-lg-7">
							<input type="text" class="form-control" id="merchantId" name="merchantId" value="<%=merchId%>" /> 
								<span id="merIdMsg" class="clr"></span>
						</div>
						<div id="div1"></div>
					</div>

					<div class="form-group row " id="payModeRef"
						style="margin-bottom: 0px; margin-bottom: 15px;">

						<label id="payModeLabel" class="col-sm-12 col-md-4 col-lg-4 col-form-label label-mandatory"
							style="margin-bottom: 21px; margin-top: 5px;">Payment Mode</label>
							
						<div class="col-sm-12 col-md-7 col-lg-7" >
							<select class="form-control" id="paymentMode" name="paymentMode"  onchange="paymodeDetails();">
								
								<option value="">Please Select</option>
							</select>
							<span id="paymodeRefMsg" class="clr" ></span>  
						</div>
					</div>
					
					<div class="form-group row " id="gatewayDiv"
						style="margin-bottom: 0px; margin-bottom: 15px; display: none;">
  
						<label id="bankListLabel" class="col-sm-12 col-md-4 col-lg-4 col-form-label label-mandatory"
							style="margin-bottom: 21px; margin-top: 5px;">Channel Bank/Gateway</label>

						<div class="col-sm-12 col-md-7 col-lg-7">
							<select class="form-control" id="gateway" name="gateway" onchange="cardDetails();">
								
							</select>
							<span id="banklistRefMsg" class="clr"   ></span>
						</div>
									 
					
					</div>
					
					<div class="form-group row " id="cardTypeDiv"
						style="margin-bottom: 0px; margin-bottom: 15px; display: none;">

						<label id="cardTypeLabel" class="col-sm-12 col-md-4 col-lg-4 col-form-label label-mandatory"
							style="margin-bottom: 21px; margin-top: 5px;">Card Type</label>
							
						<div class="col-sm-12 col-md-7 col-lg-7" >
							<select class="form-control" id="cardType" name="cardType">
								<option value="">Please Select</option>
							</select>
							<span id="cardTypeMsg" class="clr" ></span>  
						</div>
					</div>
					<div id="cardTypemsg" class="clr"  style="padding-left:325px; "></div>
					<div class="form-group row">
					<label class="col-sm-8 col-md-4 col-lg-4 col-form-label">Captcha<span style="color:red"> *</span></label>
						<div class="col-sm-12 col-md-7 col-lg-7">
							<span class="pad_LR_10 txt_11 txt_tahoma">
									 <img src="../../SimpleCaptcha.jpg" id="imgCaptcha" /> 
									
							</span>
							<a href="#" class="red-txt" id="btnChangeCaptcha" style="text-decoration: none">
							<img src="../../secure/images/captcha.png" width="35"	height="40" border="0" alt=""/>
							</a>
					
						<input name="captcha" id="captcha" class="txtbox3" maxlength="5" type="text"/>                           
						  <div id="captchaErrorMsg"></div> <span style="font-size:12px">Letters are case-sensitive</span>
						   <span class="red-txt" style="color:red"><%=msg%></span>  
						  
						  </div>
														
					</div>
					   
					
		<center><input class="btn btn-secondary btn-home " type="submit"  value="Submit"  onclick=" return encParam();" />
			<input class="btn btn-secondary addBtn mr-10 ml-10" type="button" value="Reset" onclick="resetMsg();"/>
         <input class="btn addBtn"  type="button" value="Home" onclick="window.location.href='<%=FetchProperties.getJVMProperty("SECURE")%>secure/home'"/>
       </center>
	   
		<center><div id="msgtable" class="container my-4 ">
					 <p class="headingRecord" >No Records Found</p>
					 </div>
		</center>			 
		

         
	</div>	
</div>
</div>

		<input type="hidden"  id="ref" name = "ref" />	
		<input type="hidden" id="CaptchaCount" name="CaptchaCount" value="<%=counter%>"/>
		<input type="hidden" id="CaptchaLink" name="CaptchaLink" value="<%=captchaLink%>"/>
        <input type="hidden"  id="captchaValue" name = "captchaValue" />
     	<input type="hidden"  id="merchantRefValue" name = "merchantRefValue" />
        <input type="hidden"  id="sbiepayRefValue" name = "sbiepayRefValue" />     
        <input type="hidden"  id="bankRefValue" name = "bankRefValue" />
        <input type="hidden"  id="transDateRefValue" name = "transDateRefValue" />
        <input type="hidden"  id="merchantIdValue" name = "merchantIdValue" />
        <input type="hidden"  id="paymentModeValue" name = "paymentModeValue" />
        <input type="hidden"  id="gatewayValue" name = "gatewayValue" />
        <input type="hidden"  id="cardTypeValue" name = "cardTypeValue" />
	</form>

</body>



<script type="text/javascript">
var keys;
$(document).ready(function()
		{

	       $("#msgtable").hide();

		    $("#btnChangeCaptcha").click(function(event)
		    {
		        $("#imgCaptcha").attr("src", "../../SimpleCaptcha.jpg?ran=" + Math.random());
		        $("#captcha").val('');
		        return false;

		    });

		    document.getElementById('merchantRefLabel').classList.add("label-mandatory");

		  
				getKeys();
				paymodeList();
				
			$('#ref').val('merchantRefDiv');
			

			var payMode = sessionStorage.getItem("paymentMode");
			$("#paymentMode").val(payMode);
			
			if(payMode != null){

			   $('#gatewayDiv').show();
			   $('#cardTypeDiv').show(); 

			}else{
				$('#gatewayDiv').hide();
			}
			var gatewayName = sessionStorage.getItem("gateway");
			var cardTypeName = sessionStorage.getItem("cardType");
			
			$('#gateway').html('<option selected="selected" value="'+gatewayName+'">'+gatewayName+'</option>');
			$('#cardType').html('<option selected="selected" value="'+cardTypeName+'">'+cardTypeName+'</option>');
			sessionStorage.removeItem("paymentMode");
			sessionStorage.removeItem("gateway");
			sessionStorage.removeItem("cardType");
			
		});

		

		
function getKeys() {	
	var a =  "/secure/EncryptionServlet?generateKeypair=true";
	$.jCryption.getKeys(a, function(b) {
		keys = b;
	
		
	});
  }
	var dateVal = new Date();
	
	$(function(){
		$("#txnDateRef").datepicker({
			dateFormat : 'dd-mm-yy',
		minDate: "-12m",
		maxDate: 0,
		format: "dd/mm/yyyy",
		autoclose: true,
		showHighlight: true,
		showOtherMonths: true,
		autoClose: true,
		changeMonth: true,
		changeYear: true,
		orientation: "button"
		});
	});
	
	$(function() {
		$("#txnDateRef").datepicker().focus(function() {
	        $(".ui-datepicker-prev, .ui-datepicker-next").remove();
	      });
	}); 
	
	


 function encParam(){
	
	var merchantRef=$('#merchantRef').val();
	console.log("merchantRef : "+merchantRef);
	var validation=validateForm();
	console.log('validation='+validation);
	var x = document.getElementById("captcha");
			if (x.type == "text") {
				x.type = "password";
				} else {
		x.type = "password";
		}
	if(validation==true){
		
	  $.jCryption.encrypt($('#merchantRef').val(),keys,function(merRefVal) {
	     $('#merchantRefValue').val(merRefVal);
	    // $('#merchantRef').val("");
		 $.jCryption.encrypt($('#sbiepayRef').val(),keys,function(sbiepayRefValue) {
			 $('#sbiepayRefValue').val(sbiepayRefValue);
		//	 $('#sbiepayRef').val("");
			 $.jCryption.encrypt($('#bankRef').val(),keys,function(bankRefValue) {
				 $('#bankRefValue').val(bankRefValue);
				// $('#bankRef').val("");
				 $.jCryption.encrypt($('#txnDateRef').val(),keys,function(transDateRefValue) {
					 $('#transDateRefValue').val(transDateRefValue);
					// $('#txnDateRef').val("");
					 $.jCryption.encrypt($('#merchantId').val(),keys,function(merchantId) {
						 $('#merchantIdValue').val(merchantId);
						// $('#merchantId').val("");
						 $.jCryption.encrypt($('#paymentMode').val(),keys,function(payment) {
							 $('#paymentModeValue').val(payment);
							 //$('#paymentMode').val("");
							 $.jCryption.encrypt($('#gateway').val(),keys,function(gateway) {
								 $('#gatewayValue').val(gateway);
								 //$('#gateway').val("");
								 $.jCryption.encrypt($('#cardType').val(),keys,function(cardType) {
									 $('#cardTypeValue').val(cardType);
									 //$('#cardType').val("");
									 $.jCryption.encrypt($('#captcha').val(),keys,function(captcha) {
										 $('#captchaValue').val(captcha);
										 $('#captcha').val(captcha);
										 
										 sessionStorage.setItem("gateway",$('#gateway').val());
										 sessionStorage.setItem("cardType",$('#cardType').val());
										 sessionStorage.setItem("paymentMode",$('#paymentMode').val());
										 $('#formTrans').submit();
										 });
							      	});
							 	});
						 	 });
					 	 });
				  	 });
			   	}); 
			}); 
		});
	}
	 return false;
} 

</script>


</html>



