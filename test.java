Implement a scheduler named GatewayPoolingService that will run every hour to process merchant order payments for corporate.
Scheduler execute every 2 minutes.
Requirement:


        
      
Fetch records from Table: MERCHANT_ORDER_PAYMENTS, where condition = TRASACTION_STATUS="BOOKED" or "PENDING" AND PAYMENT_STATUS="PAYMENT_INITIATION_START" or "PENDING".
It will return multiple transaction (ATRN) records.


        
      
Process transaction records using Spring Batch framework: Use Spring Batch to process the records retrieved from table: MERCHANT_ORDER_PAYMENTS. i.e. Extract multiple ATRNs along with their status and push to the KAFKA publisher.


        
      
KAFKA Consumer Handling: It consumes the Kafka messages and initiate the next process i.e. Call API based on ATRN Pay Mode.


        
      
External API Call - Payment Service API: If the transaction (ATRN) will be processed as per paymode i.e.
Paymode is = "INB" → Call the Payment Service INB API.
Paymode is = "OtherINB" → Call the Payment Service other INB API.


        
      
Retry Logic: In case of transaction failed to process,
If STATUS="PENDING" then it will retry for 24 hours. interval can be added 3 minutes hardcoded now.
If STATUS="BOOKING" then it will retry based on start time defined value in admin(For now hardcode 7 minutes). interval can be added 3 minutes hardcoded for now.
