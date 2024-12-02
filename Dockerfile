FROM openjdk:17-jdk
WORKDIR /app
COPY build/libs/admin-server-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9003
ENTRYPOINT ["java", "-jar", "app.jar"]