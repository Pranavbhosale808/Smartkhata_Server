# ===== STAGE 1: BUILD SPRING BOOT APP =====
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app

# copy everything into container
COPY . .

# build jar
RUN mvn clean package -DskipTests


# ===== STAGE 2: RUN SPRING BOOT APP =====+
FROM eclipse-temurin:17-jdk

WORKDIR /app

# copy jar from builder stage
COPY --from=builder /app/target/*.jar app.jar 

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]