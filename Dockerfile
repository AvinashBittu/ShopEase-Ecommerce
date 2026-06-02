FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/shopease-1.0.0.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "app.jar"]
