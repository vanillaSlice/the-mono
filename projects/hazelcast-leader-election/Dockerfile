FROM java:8-jdk-alpine as builder
WORKDIR /opt/app
COPY ./build.gradle ./build.gradle
COPY ./gradle ./gradle
COPY ./gradlew ./gradlew
COPY ./settings.gradle ./settings.gradle
COPY ./src ./src
RUN ./gradlew assemble

FROM java:8-jre-alpine
WORKDIR /opt/app
COPY --from=builder /opt/app/build/libs/leader-election-*.jar ./leader-election.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "./leader-election.jar"]
