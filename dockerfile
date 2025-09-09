FROM eclipse-temurin:21-jdk-slim AS build
WORKDIR /app
## ver si lo quito luego (workdir)
COPY . .
RUN mvn clean package -DskipTests -B

FROM openjdk:21-jdk-slim
COPY target/dockerized.postgresql-0.0.1-SNAPSHOT.jar java-app.jar
ENTRYPOINT ["java","-jar","java-app.jar"]
