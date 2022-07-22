FROM amazoncorretto:11-alpine3.13
LABEL maintainer="anton_narizhny"
VOLUME /main-app
ADD target/test-task-inside-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9000
ENTRYPOINT ["java", "-jar","/app.jar"]