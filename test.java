
GemFire Local Setup Instruction

Add Properties in gradle.properties
Add These properties in gradle.properties file which is inside .gradle folder.

systemProp.http.nonProxyHosts=gitlab.epay.sbi
systemProp.https.nonProxyHosts=gitlab.epay.sbi
gitlab.username=<PASS_YOUR_ADID or GITLAB_USERNAME>
gitlab.token=<PASS_YOUR_GITALAB_TOKEN>

						or
use gradle.properties file in your .gradle folder and change the gitlab credentials
https://neosofttechnologiesmail-my.sharepoint.com/personal/binoy_medhi_neosofttech_com/_layouts/15/onedrive.aspx?id=%2Fpersonal%2Fbinoy%5Fmedhi%5Fneosofttech%5Fcom%2FDocuments%2FCedge%2FOthers%2FGemFire%2Fgradle%2Eproperties&parent=%2Fpersonal%2Fbinoy%5Fmedhi%5Fneosofttech%5Fcom%2FDocuments%2FCedge%2FOthers%2FGemFire

GemFire Local Server Setup
Go to Drive inside go to this directory -- Binoy Medhi>Cedge>Others>Gemfire
or
 download it from below link
https://neosofttechnologiesmail-my.sharepoint.com/personal/binoy_medhi_neosofttech_com/_layouts/15/onedrive.aspx?id=%2Fpersonal%2Fbinoy%5Fmedhi%5Fneosofttech%5Fcom%2FDocuments%2FCedge%2FOthers%2FGemFire
Download the GireFire_local_server.zip file extract it.

Go Inside the Extracted Folder and open bin folder directory
take a terminal from bin directory
Run the cmd from GemFire bin directory
below are the commands to connect with locators, server and so on.

Commands for Start the locator and server

 To connect with gemfire
	> gfsh
To start the locator
	>  start locator --name=locator 
			or
	   (If above command is not worked, Try with this command)
	 > start locator --name=locator --bind-address=127.0.0.1
To start the server
	> start server --name=server
			or				
	(If above command is not worked, Try with this command)
	 > start server --name=server --bind-address=127.0.0.1
To create the region
	> create region --name=<REGION_NAME> --type=REPLICATE
To fetch the data from specific region
	> query --query="select * from /<REGION_NAME>"




GemFire Official Documents Go Through links 

Link for GemFire solutions in spring boot:
https://techdocs.broadcom.com/us/en/vmware-tanzu/data-solutions/spring-boot-for-tanzu-gemfire/2-0/gf-sb-2-0/index.html


Link for GemFire document:
https://techdocs.broadcom.com/us/en/vmware-tanzu/data-solutions/tanzu-gemfire/10-1/gf/about_gemfire.html


Link for OQL document interacting with GemFire:
https://gemfire.dev/tutorials/java/oql/

Github link for GemFire spring boot:
https://github.com/spring-projects/spring-data-gemfire/tree/main/src/main/java/org/springframework/data/gemfire

