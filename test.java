To meet your requirement of structuring the result such that:

1. Array 0: Contains only Transaction table data.


2. Array 1: Contains the single Order table data.



We can use JPQL with subqueries to fetch both sets of data in a single query but return them as two distinct lists. Here's how to achieve it:


---

Query

@Query("""
    SELECT 
        (SELECT t FROM Transaction t 
         WHERE t.orderRefNumber = :orderRefNumber 
         AND t.mid = :mid 
         AND (:atrnNumber IS NULL OR t.atrnNumber = :atrnNumber) 
         AND (:sbiOrderRefNumber IS NULL OR t.sbiOrderRefNumber = :sbiOrderRefNumber)) AS transactions,
         
        (SELECT o FROM Order o 
         WHERE o.orderRefNumber = :orderRefNumber 
         AND o.orderAmount = :orderAmount) AS orderData
    FROM Transaction t
""")
List<Object[]> fetchTransactionAndOrderData(
        @Param("orderRefNumber") String orderRefNumber,
        @Param("mid") String mid,
        @Param("orderAmount") BigDecimal orderAmount,
        @Param("atrnNumber") String atrnNumber,
        @Param("sbiOrderRefNumber") String sbiOrderRefNumber);


---

Explanation of the Query

1. Subquery for Transaction Table:

A subquery fetches all Transaction rows matching the filters: orderRefNumber, mid, atrnNumber (optional), and sbiOrderRefNumber (optional).

These rows are returned as a list under transactions.



2. Subquery for Order Table:

A subquery fetches only the single row from the Order table matching orderRefNumber and orderAmount.

This is returned as orderData.



3. Result Format:

The query returns two results:

Index 0: List of Transaction rows.

Index 1: A single Order row.






---

Service Layer

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Map<String, Object> getTransactionAndOrderData(String orderRefNumber, String mid, BigDecimal orderAmount, String atrnNumber, String sbiOrderRefNumber) {
        List<Object[]> rawData = transactionRepository.fetchTransactionAndOrderData(orderRefNumber, mid, orderAmount, atrnNumber, sbiOrderRefNumber);

        // Extract Transactions and Order data
        List<Transaction> transactions = (List<Transaction>) rawData.get(0); // Array of Transaction data
        Order order = (Order) rawData.get(1); // Single Order row

        // Structure the result
        Map<String, Object> result = new HashMap<>();
        result.put("transactions", transactions);
        result.put("order", order);

        return result;
    }
}


---

Controller

@RestController
@RequestMapping("/api/v1/data")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transaction-order")
    public ResponseEntity<Map<String, Object>> getTransactionAndOrderData(
            @RequestParam String orderRefNumber,
            @RequestParam String mid,
            @RequestParam BigDecimal orderAmount,
            @RequestParam(required = false) String atrnNumber,
            @RequestParam(required = false) String sbiOrderRefNumber) {

        Map<String, Object> result = transactionService.getTransactionAndOrderData(orderRefNumber, mid, orderAmount, atrnNumber, sbiOrderRefNumber);
        return ResponseEntity.ok(result);
    }
}


---

Expected Output

The output will be structured as you require:

{
  "transactions": [
    {
      "atrn

