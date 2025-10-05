FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/scraper.jar app.jar
EXPOSE 12345
ENTRYPOINT ["java", "-jar", "app.jar"]
