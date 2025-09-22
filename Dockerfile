# --- ETAPA 1: Construcción (Build) ---
FROM maven:3.8-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
# Compila y empaqueta la aplicación
RUN mvn clean package -DskipTests

# --- NUEVO PASO: Renombramos el archivo JAR a un nombre predecible ---
RUN mv /app/target/*.jar /app/target/app.jar


# --- ETAPA 2: Ejecución (Runtime) ---
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Ahora la copia es más simple y segura porque el nombre es siempre 'app.jar'
COPY --from=build /app/target/app.jar .

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]