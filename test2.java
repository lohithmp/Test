Here’s a clear, business-ready point-wise version of your explanation suitable for your Business Analyst (BA) document:


---

Epay 1.0 and Epay 2.0 Search Flow – Transaction Tracking

1. Initial Search Call (Epay 1.0):

In the Epay 1.0 application, when the user performs a transaction search, it first calls the Epay 1.0 Search API.

The API response is displayed on the Epay 1.0 JSP page.



2. Handling “No Records Found” in Epay 1.0:

If the Epay 1.0 Search API returns “No Records Found”, then the system will trigger a fallback search using Epay 2.0 Search.



3. Epay 2.0 Search Invocation:

The Epay 2.0 Search is integrated within the Epay 2.0 JSP through Java code.

This search is executed automatically when no records are found from Epay 1.0.



4. Filtering Logic in Epay 2.0:

Once records are retrieved from Epay 2.0 Search, the data will be filtered based on the user’s selected Pay Mode and Bank.

Only the filtered records will be displayed on the Epay 2.0 JSP page.



5. Final Display Logic:

If matching records are found after applying filters, they will be shown in Epay 2.0 JSP.

If no records are found even in Epay 2.0, the user will be redirected to the Epay 1.0 “No Records Found” page.





---

Would you like me to make this version more formal (for inclusion in a BRD or FSD document) or keep it as a simple functional explanation for your BA meeting notes?

