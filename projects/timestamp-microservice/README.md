# Timestamp Microservice

[![Latest Release](https://img.shields.io/github/release/vanillaSlice/TimestampMicroservice.svg)](https://github.com/vanillaSlice/TimestampMicroservice/releases/latest)
[![Build Status](https://img.shields.io/travis/com/vanillaSlice/TimestampMicroservice/master.svg)](https://travis-ci.com/vanillaSlice/TimestampMicroservice)
[![Coverage Status](https://img.shields.io/coveralls/github/vanillaSlice/TimestampMicroservice/master.svg)](https://coveralls.io/github/vanillaSlice/TimestampMicroservice?branch=master)
[![License](https://img.shields.io/github/license/vanillaSlice/TimestampMicroservice.svg)](LICENSE)

A simple timestamp microservice built with [Spring Boot](http://spring.io/projects/spring-boot). A deployed version can
be viewed [here](https://timestamp.mikelowe.xyz/).

## Getting Started

### Prerequisites

* [Java 8](https://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html)
* [Gradle](https://gradle.org) (optional)

### Building

*If you don't want to build the project yourself, head to
[releases](https://github.com/vanillaSlice/TimestampMicroservice/releases) and download one of the jar files from
there.*

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

    This will create a jar file called `timestampapp-1.0.0.jar` in `build/libs`.

### Running

From your terminal/command prompt run:

```
java -jar timestampapp-1.0.0.jar
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
