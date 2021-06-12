# Spring Boot JPA + H2 DB : Bild a CRUD Rest APIs

In this assignement, we're gonna build a Spring Boot Rest CRUD API with Maven that use Spring Data JPA to interact with H2 database. Here we can see the below things:

- How to configure Spring Data, JPA, Hibernate to work with Database
- How to define Data Models and Repository interfaces
- Way to use Spring Data JPA to interact with H2 Database

We can Run this application directly /through command line(as a standalone application).

## Run Spring Boot application
```
mvn spring-boot:run
```

we can use both swagger and postman to access the endpoints as below:

http://localhost:8080/mars/api/updateEmployeeById?id=1 - postman
http://localhost:8080/mars/swagger-ui.html#/employee-controller/updateEmployeeUsingPUT - swagger

To Run the application through Command line(as a standalone application),
- the first argument must be the action like "save/update/findByID/findAll/delete" - one of these actions
- the second and third arguments can be firstname and the surname we can pass.
