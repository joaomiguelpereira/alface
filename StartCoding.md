Getting started

# Introduction #
We'll go here through the basic steps to getting started in windows xp.

What you'll need: (TODO: Add links)

  * JDK1.5+ {TODO. Add link]
  * Maven 2+ [TODO: Check version and add link how to install]
  * Eclipse 3.4
  * Alfresco Labs 3.0 (See here details for installation [TODO: Add link])

Assumptions:
  * You have Alfresco Labs 3.0 configured and running.

## First steps ##

Checkout the folder scrumr from SVN (http://code.google.com/p/alface/source/checkout) to your local disk.

## WebClient ##

  * go to the SCRUM\_HOME/webclient, where SCRUM\_HOME is where you have your working copy of the project
  * run the command to check if everything is ok:
```
mvn compile
```
  * run the following commands if you wish to generate and eclipse project
```
mvn eclipse:eclipse
```
  * Run the following command to create a lunch configuration so you can run the GWT shel from eclipse:
```
mvn gwt:eclipse
```
  * In eclipse import-> Existing Projects into Workspace -> Select the folder SCRUM\_HOME/webclient
  * Locate the file org.nideasystems.scrumr.webclient.Application.launch
  * Right click and Run as...

if you don't see the GWT shell, or if the shell is empty, something is wrong :)


You can also start the GWT Shell using Maven:
```
mvn gwt:run
```

You can change the code while running the shell. To see the changes in action just refresh the Shell.


## Restlayer ##
  * go to SCRUM\_HOME/restlayer
  * run the following command to test if everything is ok:
```
mvn jetty:run
```
  * go to your browser, and type: http://localhost:8082/

You have started the jetty web server listening in port 8082.

  * You're promped with a username/password. Use some valid username/password from Alfresco

If everything it's ok you should a string in your browser

  * To generate the eclipse project, run the following command:
```
mvn eclipse:eclipse
```
  * Follow the same steps describe above fro webclient
  * Locate the Class org.nideasystems.scrumr.restlayer.Server
  * Right click and Run as... Java application
  * http://localhost:8181/

If everything it's ok you should be asked again for the username/passwod :)

Turn your refactoring on :)