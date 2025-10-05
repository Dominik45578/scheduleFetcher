FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/auth.jar app.jar
EXPOSE 12346
ENTRYPOINT ["java", "-jar", "app.jar"]
