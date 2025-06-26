FROM amazoncorretto:17

WORKDIR /app

COPY target/*.jar frontend.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "frontend.jar"]
