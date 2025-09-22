# Usamos una imagen base oficial de Java 17
FROM eclipse-temurin:17-jdk-jammy

# Definimos un argumento para el nombre del archivo JAR
ARG JAR_FILE=target/*.jar

# Copiamos el archivo .jar compilado de tu proyecto al contenedor
COPY ${JAR_FILE} app.jar

# Exponemos el puerto 8080 para que el exterior pueda comunicarse con nuestra app
EXPOSE 8080

# El comando que se ejecutará cuando el contenedor inicie
ENTRYPOINT ["java","-jar","/app.jar"]