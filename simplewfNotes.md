The workflow implemented works but still has some issues, please follow the instructions bellow

to deploy directly in alfresco:
> - copy all the files under .../src/main/jpdl/simplewf/simplewf/ except the gpd.xml and processimage.jpg to: TOMCAT\_HOME/shared/classes/alfresco/extension

> - change the name of the file "processdefinition.xml" to "simplewf\_processdefinition.xml"

> - restart alfresco

At the moment the task will always be assigned to the admin user...


you can also deploy the process using eclipse, just follow the instructions on: http://wiki.alfresco.com/wiki/WorkflowAdministration - Deploying via JBoss jBPM Process Designer