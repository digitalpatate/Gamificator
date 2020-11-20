FROM openjdk:11-jre-slim

COPY impl/target/*.jar fruits-impl-1.0.0.jar
ENTRYPOINT ["java","-jar","fruits-impl-1.0.0.jar"]
