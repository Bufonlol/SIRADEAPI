FROM openjdk:19-jdk AS build
WORKDIR /app

# Configurar encoding UTF-8
ENV LANG C.UTF-8
ENV LC_ALL C.UTF-8

COPY pom.xml .
COPY src src
COPY mvnw .
COPY .mvn .mvn

RUN chmod +x ./mvnw
# Ejecutar con encoding UTF-8 y actualizar plugin de recursos
RUN ./mvnw clean package -DskipTests -Dfile.encoding=UTF-8 -Dmaven.resources.plugin.version=3.3.1

FROM openjdk:19-jdk
VOLUME /tmp
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080