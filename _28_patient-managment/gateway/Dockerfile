FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn dependency:go-offline -B
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim AS runner

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]

#docker run -d  --name api-gateway-patients-container -p 8083:8083 --network internal-network api-gateway-patients:latest

#docker build -t api-gateway-patients .


