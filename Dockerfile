# Dockerfile
FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY build/libs/Bagueton_API.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

