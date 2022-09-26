# Recipes Application (SpringBoot)

## Requirements

For building and running the application you need:

- [JDK 11](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
- Any SQL based database (optional)

This project is using [H2 Database](https://www.h2database.com/html/main.html) which is a Embedded and server modes;
in-memory databases.

## Running the application locally

* Clone this repository
* Make sure you are using JDK 1.8 and Maven 3.x
* You can build the project and run the tests by running

```shell
mvn clean package
```

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main`
method
in the `com.books.recipes.RecipesApplication` class from your IDE.

Alternatively you can use
the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html)
like so:

```shell
mvn spring-boot:run
```

## Running tests

This application developed with JUnit and Mockito Spring boot tests, by excepting the below command will run different
tests

```shell
mvn test
```

## API documentation

To access the API documentation which is generated by [Swagger UI / Open API](https://swagger.io/docs/).

* Run the application
* Go to [SwaggerUI](http://localhost:8080/swagger-ui/index.html)