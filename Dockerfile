FROM maven:3.9.9-ibm-semeru-21-jammy AS builder
WORKDIR /
COPY pom.xml .
RUN mvn dependency:go-offline
COPY /src /src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080