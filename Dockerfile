
# DOCKER FILE MULTISTAGE

# Build the image
FROM maven:3.6.3-openjdk-17 AS build

# Working directory
WORKDIR /app

# Copy source code
COPY pom.xml .
COPY src ./src

# Build app
RUN mvn clean package -DskipTests

# -----------------------------------------------
# STAGE 2
FROM azul/zulu-openjdk:17.0.13-jre

# Set working dir
WORKDIR /app

# Copy jar file from the previous build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run jar file
ENTRYPOINT ["java", "-jar", "app.jar"]