# Dockerfile para SIRADEAPI

# Etapa de compilación
FROM maven:3.8.2-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn clean package -DskipTests

# Etapa de empaquetado
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/SIRADEAPI-0.0.1-SNAPSHOT.jar SIRADEAPI.jar
ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "SIRADEAPI.jar"]
