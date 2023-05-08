FROM openjdk:19-jdk-alpine

WORKDIR /mentors_joy

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} api-application.jar

ENTRYPOINT ["java", "-jar", "api-application.jar"]