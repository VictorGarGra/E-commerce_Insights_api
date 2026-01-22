# --- Etapa 1: La Construcción (El Taller ) ---
FROM eclipse-temurin:17-jdk-jammy AS build

# Establecemos el directorio de trabajo
WORKDIR /workspace/app

# 1. PRIMERO copiamos el archivo mvnw
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# 2. DESPUÉS le damos permisos (ahora que ya existe en el contenedor)
RUN chmod +x mvnw

# Descargamos dependencias
RUN ./mvnw dependency:go-offline

# Copiamos el código y compilamos
COPY src src
RUN ./mvnw package -DskipTests

# --- Etapa 2: La Ejecución (El Escenario ) ---
FROM eclipse-temurin:17-jre-jammy
WORKDIR /workspace/app

# Copiamos el JAR generado
COPY --from=build /workspace/app/target/*.jar app.jar

# Google Cloud Run usa la variable $PORT, así que la inyectamos al arrancar
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]