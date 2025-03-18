# Etapa de compilación
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY . .
RUN mvn clean package -Pprod -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copiar el JAR compilado
COPY --from=build /app/target/*.jar app.jar

# Si tu script Python no está incluido dentro del JAR, copia también la carpeta o el archivo
# Ejemplo:
# COPY python-model/ /app/python-model/

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]