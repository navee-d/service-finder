# --- STAGE 1: Build ---
FROM maven:3.9-eclipse-temurin-17-alpine AS builder

ARG MODULE_NAME
WORKDIR /build

COPY . .

RUN mvn -f ${MODULE_NAME}/pom.xml \
    clean package -DskipTests \
    -Dmaven.repo.local=/root/.m2/repository \
    -Dhttp.keepAlive=false \
    -Dmaven.wagon.http.pool=false \
    -Dmaven.wagon.http.retryHandler.count=5 \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=25


# --- STAGE 2: Run ---
FROM eclipse-temurin:17-jdk-alpine

# 🔥 MUST be declared AGAIN *before* COPY
ARG MODULE_NAME

WORKDIR /app

# 🔥 MODULE_NAME now correctly expands
COPY --from=builder /build/${MODULE_NAME}/target/*.jar app.jar

EXPOSE 8080 8761

ENTRYPOINT ["java", "-jar", "app.jar"]
