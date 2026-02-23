# ===== STAGE 1: BUILD SPRING BOOT APP =====
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests


# ===== STAGE 2: RUN SPRING BOOT APP =====
FROM eclipse-temurin:17-jdk

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["sh","-c","java -jar app.jar --server.port=${PORT}"]