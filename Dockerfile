# Use official Eclipse Temurin (OpenJDK) image for Java 21
FROM eclipse-temurin:21-jdk-jammy as builder

# Set working directory
WORKDIR /app

# Copy only the files needed for Maven to resolve dependencies
COPY pom.xml .
COPY src ./src

# Build the application with Maven
RUN ./mvnw clean package -DskipTests

# Second stage: runtime image
FROM eclipse-temurin:21-jre-jammy

# Set working directory
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port your app runs on (change if needed)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
