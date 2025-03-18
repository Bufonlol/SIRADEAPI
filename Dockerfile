# Etapa de compilación
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY . .
RUN mvn clean package -Pprod -DskipTests

# tapa de ejecución
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Instalar Python en el contenedor
RUN apt-get update && apt-get install -y python3 python3-pip && rm -rf /var/lib/apt/lists/*
COPY requirements.txt /app/requirements.txt
RUN pip3 install -r src/main/java/com/sirade/SIRADEAPI/requirements.txt

# Copiar el JAR compilado
COPY --from=build /app/target/*.jar app.jar
# ...
COPY src/main/java/com/sirade/SIRADEAPI/python_model/script.py /app/script.py
COPY src/main/java/com/sirade/SIRADEAPI/python_model/encoder.pkl /app/encoder.pkl
COPY src/main/java/com/sirade/SIRADEAPI/python_model/modelo_svc.pkl /app/modelo_svc.pkl
COPY src/main/java/com/sirade/SIRADEAPI/python_model/scaler.pkl /app/scaler.pkl

# ...


# Si tu script Python no está incluido dentro del JAR, copia también la carpeta o el archivo
# Ejemplo:
# COPY python-model/ /app/python-model/

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
