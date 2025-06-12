FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn dependency:go-offline -B
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim AS runner

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "app.jar"]

#Auth service container creation
#docker build -t auth-service .

#docker run -d  --name auth-service-container --network internal-network -e DB_URL=jdbc:postgresql://auth-service-db:5432/auth-service-db -e DB_USERNAME=admin -e DB_PASSWORD=admin -e SPRING_JPA_HIBERNATE_DDL_AUTO=update -e SPRING_SQL_INIT_MODE=always -e JWT_SECRET=X6krczRMzNMW6kJf07Sk7QMXKzN0kEh+aZ+BZu9Qf3k= auth-service

    
#Auth db container creation

#docker run -d --name auth-service-db --name auth-service-db --network internal-network -e POSTGRES_DB=auth-service-db -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -v auth-db-data:/var/lib/postgresql/data -p 5001:5432 postgres:latest