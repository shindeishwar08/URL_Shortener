# Stage 1: Build the application
# Use an official Maven image to build our .jar file
FROM maven:3.8-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file first to cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code and build the app
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final, lightweight image
# Use a slim Java 17 image to run the app
FROM openjdk:17-slim

# Set the working directory
WORKDIR /app

# Copy the .jar file from the 'build' stage
COPY --from=build /app/target/url-shortener-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 (the port our Spring app runs on)
EXPOSE 8080

# The command to run when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]