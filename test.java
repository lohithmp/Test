GemFire Local Setup Guide

This guide provides step-by-step instructions for setting up GemFire locally, configuring Gradle properties, and running a GemFire server.


---

1. Configuring Gradle Properties

To set up GemFire with Gradle, update the gradle.properties file inside the .gradle folder.

Steps:

1. Open the .gradle folder in your system.


2. Locate the gradle.properties file (or create one if it does not exist).


3. Add the following properties to the file:

systemProp.http.nonProxyHosts=gitlab.epay.sbi
systemProp.https.nonProxyHosts=gitlab.epay.sbi
gitlab.username=<YOUR_ADID_OR_GITLAB_USERNAME>
gitlab.token=<YOUR_GITLAB_TOKEN>


4. Alternatively, you can use a pre-configured gradle.properties file from the following link and update it with your credentials:
Download gradle.properties




---

2. Setting Up a Local GemFire Server

Downloading the GemFire Server Files

1. Navigate to the following directory on your system:

Binoy Medhi > Cedge > Others > GemFire

OR


2. Download the GemFire server files from the link below:
Download GemFire_local_server.zip


3. Extract the GemFire_local_server.zip file.


4. Open the extracted folder and navigate to the bin directory.


5. Open a terminal in the bin directory.




---

3. Running GemFire Locally

Starting GemFire Services

1. Open the GemFire shell (GFSH)

gfsh


2. Start the locator

start locator --name=locator

OR (if the above command does not work)

start locator --name=locator --bind-address=127.0.0.1


3. Start the server

start server --name=server

OR (if the above command does not work)

start server --name=server --bind-address=127.0.0.1



Creating and Querying a Region

1. Create a region

create region --name=<REGION_NAME> --type=REPLICATE


2. Query data from a specific region

query --query="SELECT * FROM /<REGION_NAME>"




---

4. Official GemFire Documentation & Resources

Spring Boot Integration with GemFire:
Spring Boot for Tanzu GemFire

GemFire Official Documentation:
Tanzu GemFire Documentation

OQL (Object Query Language) Guide for GemFire:
GemFire OQL Tutorial

Spring Boot GemFire GitHub Repository:
Spring Data GemFire GitHub



---

Conclusion

Following this guide, you should be able to set up and run a GemFire server locally, configure Gradle properties, create regions, and query data efficiently. For further support, refer to the official documentation provided above.

