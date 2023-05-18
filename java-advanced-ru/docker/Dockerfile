FROM gradle:7.4.0-jdk17
WORKDIR /app
COPY ./app .
ARG JAR_FILE=/app/build/libs/simple-application.jar
COPY ${JAR_FILE} simple-application.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "simple-application.jar"]