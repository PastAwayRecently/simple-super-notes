# Use the official OpenJDK image as the base
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file of your application into the container
COPY target/simple-super-notes.jar /app/simple-super-notes.jar

# Set the command to run the application
CMD ["java", "-jar", "simple-super-notes.jar"]

# Expose the port that the application will listen on
EXPOSE 8080