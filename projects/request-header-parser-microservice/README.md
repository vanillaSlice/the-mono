# Request Header Parser Microservice

[![Build Status](https://img.shields.io/github/actions/workflow/status/vanillaSlice/the-mono/request-header-parser-microservice.yml?branch=main)](https://github.com/vanillaSlice/the-mono/actions?query=workflow%3ARequest-Header-Parser-Microservice+branch%3Amain)
[![Coverage Status](https://img.shields.io/codecov/c/gh/vanillaSlice/the-mono/main?flag=RequestHeaderParserMicroservice)](https://codecov.io/gh/vanillaSlice/the-mono/tree/main/projects/request-header-parser-microservice)
[![License](https://img.shields.io/badge/license-MIT-green)](LICENSE)

A simple request header parser microservice built with [Spring Boot](http://spring.io/projects/spring-boot). A deployed
version can be viewed [here](https://headerparser.mikelowe.xyz/).

## Getting Started

### Prerequisites

* [Java 8](https://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html)
* [Gradle](https://gradle.org) (optional)

### Building

To build the project yourself:

1. Clone the project.
2. Navigate to the project directory in your terminal/command prompt.
3. If you have Gradle installed locally, run the Gradle Daemon:

    ```
    gradle clean assemble
    ```

   If you don't have Gradle installed locally and are running on a Unix-like platform such as Linux or macOS, run:

    ```
    ./gradlew clean assemble
    ```

   If you don't have Gradle installed locally and are running on Windows, run:

    ```
    gradlew clean assemble
    ```

    This will create a jar file called `request-header-parser-app-1.0.0.jar` in `build/libs`.

### Running

From your terminal/command prompt run:

```
java -jar request-header-parser-app-1.0.0.jar
```

Point your browser to [localhost:8080](http://localhost:8080) to see the [Swagger](https://swagger.io/) documentation.

## Technology Used

For those of you that are interested, the technology used in this project includes:

* [Java 8](https://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html)
* [Spring Boot](http://spring.io/projects/spring-boot)
* [Swagger](https://swagger.io/) (API documentation)
* [JUnit 5](https://junit.org/junit5/) and [Mockito](https://site.mockito.org/) (for testing)
* [Gradle](https://gradle.org) (for building and dependency management)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
