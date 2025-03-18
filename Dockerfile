# Etapa de compilación
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn dependency:go-offline
RUN mvn clean package -Pprod -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Instalar Python en el contenedor
RUN apt-get update && apt-get install -y python3 python3-pip && rm -rf /var/lib/apt/lists/*

# Copiar requirements.txt e instalar dependencias Python
COPY src/main/java/com/sirade/SIRADEAPI/requirements.txt /app/requirements.txt
RUN pip3 install -r /app/requirements.txt

# Copiar el JAR compilado
COPY --from=build /app/target/*.jar app.jar

# Copiar archivos del modelo y script Python
COPY src/main/java/com/sirade/SIRADEAPI/python_model/script.py /app/script.py
COPY src/main/java/com/sirade/SIRADEAPI/python_model/encoder.pkl /app/encoder.pkl
COPY src/main/java/com/sirade/SIRADEAPI/python_model/modelo_svc.pkl /app/modelo_svc.pkl
COPY src/main/java/com/sirade/SIRADEAPI/python_model/scaler.pkl /app/scaler.pkl

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
