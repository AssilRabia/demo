# DEMO - App

#


## Descriptions
- Demo is a springBoot application wish manage users by adding them 
and getting the details of registered users one by one through their Ids.

## Project Requirements

- OpenJDK 8

## Run Project

```bash
# Open terminal(linux) or cmd(windows)
cd <workspace>
mvn spring-boot:run
```

## API

- Access
    - Server : http://localhost:8080
    - Server API : http://localhost:8080/users
    - Swagger UI : http://localhost:8080/swagger-ui/index.html
    - Open Api Docs: http://localhost:8080/api-docs

## DataSource

For dev purpose an in memory H2 database is available.
It will be reset with the re-launch of the application.\

- Access
  - h2-console : http://localhost:8080/h2-console
  - credentials : 
    - Driver: org.h2.Driver
    - Url: jdbc:h2:mem:myapp_db
    - User: myapp
    - Password: myapp

## Javadoc

```bash
# Open terminal(linux) or cmd(windows)
cd <workspace>
mvn javadoc:javadoc
cd <workspace>\site\apidocs\com\example\demo # You will find javadocs there
```

## Architecture

This project is based on MVC Architecture.
