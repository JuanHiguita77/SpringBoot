FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn dependency:go-offline -B
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim AS runner

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]

#docker run -d  --name analytics-service-container -p 8082:8082 --network internal-network -e SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092 analytics-service

#docker build -t analytics-service .