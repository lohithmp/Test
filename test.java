Here's a document outlining the steps to handle CDC-triggered events, ensuring data consistency in the cache when changes occur in related tables.


---

Handling CDC-Triggered Events and View Schema in Cache

Overview

This document outlines the approach to handling CDC events for multiple related tables, ensuring that a view schema is correctly created and updated in the cache.

Problem Statement

Oracle CDC triggers Kafka Publisher whenever a table (customer or merchant) is modified.

Spring Boot Kafka Consumer processes the events and updates the cache.

A view schema in the cache depends on multiple tables.

If customer data is modified, the corresponding merchant data may not be available in the cache yet, causing potential data loss.

Similarly, if merchant data is modified first, the related customer data may not be in the cache.



---

Solution Approach

1. Design a Staging Cache for Partial Data

Since table updates are independent, we need a way to store partial updates and merge them when the related table update arrives.

Introduce a Staging Cache Region:

Store customer records temporarily if merchant data is missing.

Store merchant records temporarily if customer data is missing.

Once both related data elements are available, merge and update the view schema.



2. Implement Data Processing in Spring Boot Kafka Consumer

a. Consume Kafka Messages and Identify the Table

@KafkaListener(topics = "customer_updates", groupId = "cache_group")
public void processCustomerUpdate(String message) {
    Customer customer = parseCustomer(message);
    updateCacheWithDependency("customer", customer.getCid(), customer);
}

@KafkaListener(topics = "merchant_updates", groupId = "cache_group")
public void processMerchantUpdate(String message) {
    Merchant merchant = parseMerchant(message);
    updateCacheWithDependency("merchant", merchant.getCid(), merchant);
}

b. Check Dependencies and Merge Data

private void updateCacheWithDependency(String table, String cid, Object data) {
    if (table.equals("customer")) {
        Merchant merchant = stagingCache.get(cid);
        if (merchant != null) {
            ViewSchema view = createViewSchema((Customer) data, merchant);
            gemfireCache.put(cid, view);
            stagingCache.remove(cid); // Clean staging once processed
        } else {
            stagingCache.put(cid, data); // Store temporarily
        }
    } else if (table.equals("merchant")) {
        Customer customer = stagingCache.get(cid);
        if (customer != null) {
            ViewSchema view = createViewSchema(customer, (Merchant) data);
            gemfireCache.put(cid, view);
            stagingCache.remove(cid);
        } else {
            stagingCache.put(cid, data);
        }
    }
}

3. View Schema Creation Logic

private ViewSchema createViewSchema(Customer customer, Merchant merchant) {
    return new ViewSchema(customer.getCid(), customer.getName(), merchant.getMerchantDetails());
}


---

Expected Behavior

1. Customer table is updated first:

Kafka event is triggered.

The customer record is temporarily stored in the staging cache.

Once the merchant update arrives, both records are merged and stored in GemFire cache.



2. Merchant table is updated first:

Kafka event is triggered.

The merchant record is stored in the staging cache.

When the corresponding customer update arrives, the view schema is created and stored in GemFire cache.





---

Conclusion

This approach prevents data loss by ensuring that both customer and merchant records are available before inserting them into the final cache. The staging cache acts as a temporary storage mechanism until dependent data is available.

