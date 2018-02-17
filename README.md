# libraryapi
Library REST API Project
The purpose of this exercise is to create the server-side portion of a system in charge of managing a library’s catalog of books. The system also needs to be able to track library members and which books they currently have checked out. Since this is only the server-side portion of the application, you'll need to provide a REST API for the client to use.

How to Run

This application is packaged as a war which has Tomcat 8 embedded. No Tomcat or JBoss installation is necessary. 

  Clone this repository
  
  Make sure you are using JDK 1.8 and Maven 3.x
  
  You can build the project and run the tests by running mvn clean package
  
  Once successfully built, you can run the service by:
  
  mvn spring-boot:run -Drun.arguments="spring.profiles.active=test"
