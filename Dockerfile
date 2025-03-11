# Dockerfile para SIRADEAPI

# Etapa de compilación
FROM maven:3.8.2-jdk-11 AS build
WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn clean package -Pprod -DskipTests

# Etapa de empaquetado
FROM openjdk:11-jdk-slim
WORKDIR /app
COPY --from=build /app/target/SIRADEAPI-0.0.1-SNAPSHOT.jar SIRADEAPI.jar
ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "SIRADEAPI.jar"]
