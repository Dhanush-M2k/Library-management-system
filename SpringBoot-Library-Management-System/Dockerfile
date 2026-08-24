# ---------- Stage 1: Build ----------
# Using the official maven image directly (instead of ./mvnw) avoids depending
# on the .mvn/ wrapper folder being present in the build context — that
# hidden dotfolder is easy to lose in zip/upload/Windows-git-GUI workflows.
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml first (better Docker layer caching: dependencies are only
# re-downloaded when pom.xml actually changes)
COPY pom.xml ./pom.xml
RUN mvn dependency:go-offline -B

# Copy source code and build the jar
COPY src ./src
RUN mvn clean package -DskipTests -B

# ---------- Stage 2: Run ----------
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Run as a non-root user
RUN useradd -m appuser
USER appuser

# Copy the built jar (name-agnostic, works regardless of artifactId/version)
COPY --from=build /app/target/*.jar app.jar

# Render injects PORT at runtime; application.properties reads it via
# server.port=${PORT:8080}, so no shell substitution is needed here.
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
