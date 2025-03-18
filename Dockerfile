# Etapa de compilación
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn dependency:go-offline
RUN mvn clean package -Pprod -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Instalar Python y herramientas necesarias (se omite python3-distutils)
RUN apt-get update && apt-get install -y \
    python3 python3-venv \
    && rm -rf /var/lib/apt/lists/*

# (Opcional) Establecer variable de entorno para que setuptools use la implementación estándar de distutils
ENV SETUPTOOLS_USE_DISTUTILS=stdlib

# Copiar requirements.txt
COPY requirements.txt /app/requirements.txt

# Crear un entorno virtual con python3, actualizar pip, setuptools y wheel, e instalar dependencias (usando solo binarios)
RUN python3 -m venv /app/venv \
    && /app/venv/bin/pip install --upgrade pip \
    && /app/venv/bin/pip install "setuptools>=68.0.0" "wheel>=0.38.4" \
    && /app/venv/bin/pip install --only-binary=:all: -r /app/requirements.txt

# Copiar el JAR compilado
COPY --from=build /app/target/*.jar app.jar

# Copiar archivos del modelo y script Python
COPY src/main/java/com/sirade/SIRADEAPI/python_model/*.py /app/
COPY src/main/java/com/sirade/SIRADEAPI/python_model/*.pkl /app/

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
