# Use a lightweight OpenJDK 17 runtime base image [cite: 63]
FROM openjdk:17-jdk-slim

# Set the internal container directory [cite: 64]
WORKDIR /app

# Copy the compiled jar from your local target folder [cite: 65]
COPY target/booking-service-day8-0.0.1-SNAPSHOT.jar app.jar

# Command to execute the microservice inside the container [cite: 66]
ENTRYPOINT ["java", "-jar", "app.jar"]