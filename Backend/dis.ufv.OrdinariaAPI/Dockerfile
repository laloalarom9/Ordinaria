FROM amazoncorretto:17

WORKDIR /app

COPY target/*.jar backend.jar
COPY data ./data
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "backend.jar"]
