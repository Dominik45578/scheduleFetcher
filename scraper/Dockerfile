FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/scraper.jar app.jar

EXPOSE 12345 50051

ENTRYPOINT ["java", "-jar", "app.jar"]