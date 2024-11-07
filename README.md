The Cantic Thrallnet:

What is the Cantic Thrallnet?
In 40k lore it is an archaeotech relic used by the Adeptus Mechanicus as a neural net to mentally link soldiers with their commanding officer. As such, this is a reference tool to quickly look up 40k faction and unit rules on the fly. It is also a way for me to get more experience building applications. 

How does it work?
The application downloads faction and unit data from Wahapedia, and parses them into a local database. The UI queries the database to show you information about each faction.

**Please note**
Since the file downloads and database handling are done locally, it takes like five minutes to initialize the data.

The initial release is for testing purposes. 

Known Issues:
-Performance issues during data initialization, and loading the faction screen for factions that have a lot of units or detachments.
-Some visual issues showing unit statistics. More work needs to be done to make the UI scale properly. 
-Installing and uninstalling leave a background MSI Installer process. I'm doing more research on how to package the build without this, as it appears to be caused by my packing via jpackage and the Wix toolset. It is a benign process that takes few system resources, and can be safely terminated regardless of whether or not the application is running. 
