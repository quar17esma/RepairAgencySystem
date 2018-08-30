# Repair Agency System

Web Application is a repair agency system.
Client can add the applications to repair the products and leave the feedbacks on completed applications.
Manager can accept the application and set the price for the service or can decline it and set the reason.
Repairer can complete the accepted applications.

Data stored in DB, with access provided using JDBC API and Connection Pool on MySQL.
Architecture based on MVC and other GoF design patterns.
Application uses POJOs, Servlets and JSPs, HTML5, JSTL and custom tags, filters.
Servlet Container used in project is Apache Tomcat 8.0. Apache Maven used for building.
Logging with Log4j.  Tests are written on JUnit and Mockito.

## Built With

[Maven](https://maven.apache.org/) - Dependency Management

## Running the application

```
mvn exec:java -Dexec.mainClass="com.quar17esma.App"
```
