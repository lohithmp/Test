{
   "data": [
      {
         "orderInfo": {
                 “order Id”: “string”,
                 “merhcantOrderNumber”: “string”,
                 “orderStatus”: “string”,
                   “Currency”: string,
                  .....
          },
         “customerInfo”: {
                 “name”: “string”,
                 “phoneNumber”: “string”,
                 “email”: “string”,
                  “accountNumber”: “string”,
                  .....
           },
          “payment”:[ {
    	    “atrn”:”string”
                   “orderAmount”: number,
                   “gstAmount”: number,
                   “feesAmount”: number,
                   “chargesAmount”: number,
                   “totalAmount”: number,
                  “paymode”: string,
                 “transactionStatus”: string,
                  “bankTxnNumber”: string,
                 “processor”: string,
                 “transactionTime: TimeStamp;
           }, {
    “atrn”:”string”
                   “orderAmount”: number,
                   “gstAmount”: number,
                   “feesAmount”: number,
                   “chargesAmount”: number,
                   “totalAmount”: number,
                  “paymode”: string,
                “transactionStatus”: string,
                 “bankTxnNumber”: string,
               “processor”: string,
               “transactionTime: TimeStamp
           }]],
      
               }
   ],
   "count":“1”,
   "size":“1”,
   "status":"1"
}
