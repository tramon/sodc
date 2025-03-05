FROM openjdk:23 AS build
EXPOSE 8081
WORKDIR /app
COPY build/libs/sodc-0.0.1-SNAPSHOT.jar user-service.jar
CMD ["java", "-jar", "user-service.jar"]