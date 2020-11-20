FROM openjdk:11-jre-slim

COPY impl/target/*.jar server.jar
ENTRYPOINT ["java","-jar","server.jar"]
