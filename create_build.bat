call mvn clean
call mvn install
cd target
jpackage --type exe --input . --dest . --main-jar .\TheCanticThrallnet-1.0-SNAPSHOT-shaded.jar --main-class com.theornithologist.thecanticthrallnet.Main --name CanticThrallnet --win-shortcut --icon C:\Users\Scott\Documents\ProgrammingStuff\TheCanticThrallnet\src\main\resources\com\theornithologist\thecanticthrallnet\icons\CanticThrallnetIco.ico