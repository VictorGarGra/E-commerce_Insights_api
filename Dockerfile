FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src src
RUN ./mvnw package -DskipTests

FROM eclipse-temurin:17-jre-jammy
WORKDIR /workspace/app
COPY --from=build /workspace/app/target/*.jar app.jar

ENV JAVA_OPTS="-Xms128m -Xmx512m"

ENTRYPOINT ["java", "-jar", "app.jar"]

