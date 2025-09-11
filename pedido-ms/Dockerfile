FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/pedido-ms-*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]


