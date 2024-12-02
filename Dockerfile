# Étape 1 : Build
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Image finale
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /target/VeryCoolApp-0.0.1-SNAPSHOT.jar verycool.jar
ENTRYPOINT ["java", "-jar", "verycool.jar", "--spring.profiles.active=dev"]