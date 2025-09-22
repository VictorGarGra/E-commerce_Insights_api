# --- ETAPA 1: Construcción (Build) ---
# Usamos una imagen que tiene Maven y Java JDK para compilar el proyecto
FROM maven:3.8-openjdk-17 AS build

# Establecemos el directorio de trabajo
WORKDIR /app

# Copiamos el archivo pom.xml y descargamos las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el resto del código fuente
COPY src ./src

# Compilamos la aplicación, omitiendo las pruebas. Esto creará la carpeta /app/target
RUN mvn clean package -DskipTests


# --- ETAPA 2: Ejecución (Runtime) ---
# Usamos una imagen más ligera que solo tiene Java JRE para correr la aplicación
FROM eclipse-temurin:17-jre-jammy

# Establecemos el directorio de trabajo
WORKDIR /app

# Copiamos ÚNICAMENTE el archivo .jar desde la etapa de 'build'
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto
EXPOSE 8080

# Comando para iniciar la aplicación
ENTRYPOINT ["java","-jar","/app.jar"]