# Use a lightweight Java 17 image
FROM eclipse-temurin:17-jdk-alpine

# This argument allows us to use one Dockerfile for all services
ARG MODULE_NAME

WORKDIR /app

# Copy the compiled JAR file from the specific service folder
COPY ${MODULE_NAME}/target/*.jar app.jar

# Expose common ports (8080 for Gateway, 8761 for Discovery, others mapped dynamically)
EXPOSE 8080 8761

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]