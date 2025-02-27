Here’s a ticket description for your requirement:


---

Ticket: Implement GatewayPoolingService Scheduler for Merchant Order Payments Processing

Description:

Implement a scheduler named GatewayPoolingService that will run every hour to process merchant order payments.

Requirements:

1. Fetch Data from merchant_order_payments Table:

Retrieve records where:

transaction_status is "booked" or "pending"

payment_status is "payment_initiation_start" or "pending"




2. Process Transactions Using Spring Batch:

Extract multiple ATRNs and their statuses.

Push the data to a Kafka publisher.



3. Kafka Consumer Handling:

Consume the Kafka messages and process the status.

Implement 3 retry attempts in case of failures.



4. External API Call - Payment Service API:

If the transaction status received is:

"INB" → Call the Payment Service API accordingly.

Other than "INB" → Handle based on business logic.





Acceptance Criteria:

The scheduler should trigger automatically every hour.

Messages should be published to Kafka without data loss.

Retries should be handled properly for failures.

Payment Service API should be called based on the transaction status.


Let me know if you need any refinements!

	
