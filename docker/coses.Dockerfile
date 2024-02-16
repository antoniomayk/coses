FROM amazoncorretto:17

# Expose port 8080
EXPOSE 8080

# Create a volume for temporary files
VOLUME /tmp

# Define argument for JAR filename with default value
ARG JAR_PATH=coses-0.0.1-SNAPSHOT.jar

# Copy JAR file with user-provided name or default
COPY ${JAR_PATH} /coses-0.0.1-SNAPSHOT.jar

# Set working directory
WORKDIR /app

# Entrypoint command
ENTRYPOINT ["java", "-jar", "/coses-0.0.1-SNAPSHOT.jar"]
