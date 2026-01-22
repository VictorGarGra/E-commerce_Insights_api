FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /workspace/app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x mvnw  # Paso crítico para evitar errores de ejecución
RUN ./mvnw dependency:go-offline
COPY src src
RUN ./mvnw package -DskipTests

FROM eclipse-temurin:17-jre-jammy
WORKDIR /workspace/app
COPY --from=build /workspace/app/target/*.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]