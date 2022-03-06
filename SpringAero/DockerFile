FROM openjdk:8-jdk-alpine
COPY ./target/SpringAero-0.0.1-SNAPSHOT.jar spring.jar
ENV DBHOST = overriden_host
ENTRYPOINT ["java", "-jar", "-Ddb.hostname=${DBHOST} ","spring.jar"]
EXPOSE 8080
