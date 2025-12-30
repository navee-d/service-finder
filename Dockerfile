# --- STAGE 1: Build ---
FROM maven:3.9-eclipse-temurin-17-alpine AS builder

# 1. Declare the argument so Docker accepts it from docker-compose
ARG MODULE_NAME

WORKDIR /build

# 2. Copy the entire project source code into the container
COPY . .

# 3. Build only the specific module passed via MODULE_NAME
#    This uses the argument you just set in docker-compose.yml
RUN mvn -f ${MODULE_NAME}/pom.xml clean package -DskipTests

# --- STAGE 2: Run ---
FROM eclipse-temurin:17-jdk-alpine

# We need to redeclare ARG here because arguments reset after a new FROM
ARG MODULE_NAME

WORKDIR /app

# 4. Copy the compiled JAR file from the builder stage
#    Note: We use ${MODULE_NAME} to find the correct target folder
COPY --from=builder /build/${MODULE_NAME}/target/*.jar app.jar

EXPOSE 8080 8761

ENTRYPOINT ["java", "-jar", "app.jar"]