FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn dependency:go-offline -B
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim AS runner

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

#docker run -d  --name patient-service-container -p 8080:8080 --network internal-network -e BILLING_SERVICE_ADDRESS=billing-service-container -e BILLING_SERVICE_GRPC_PORT=9091 -e SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092 -e DB_URL=jdbc:postgresql://postgres-container:5432/patientdb -e DB_USERNAME=admin -e DB_PASSWORD=123 patient-service

#docker build -t patient-service .