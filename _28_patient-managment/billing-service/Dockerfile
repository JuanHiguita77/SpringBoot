FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn dependency:go-offline -B
RUN mvn clean package

FROM openjdk:21-jdk-slim AS runner

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8081
EXPOSE 9091

ENTRYPOINT ["java", "-jar", "app.jar"]

#docker run -d --name billing-service-container -p 8081:8081 -p 9091:9091 --network internal-network billing-service