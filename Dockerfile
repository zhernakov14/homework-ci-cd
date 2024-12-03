FROM openjdk:17-jdk-slim
WORKDIR /app

RUN apt-get update && apt-get install -y maven
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src /app/src
RUN mvn clean package
COPY target/HomeworkCICD-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9003
ENTRYPOINT ["java", "-jar", "app.jar"]