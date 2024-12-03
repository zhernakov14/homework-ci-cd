FROM jenkins/jenkins:lts
USER root
RUN apt-get update && apt-get install -y maven
RUN apt-get update && apt-get install -y docker.io

#COPY src /app/src
#RUN mvn clean package
#COPY target/HomeworkCICD-0.0.1-SNAPSHOT.jar app.jar
#EXPOSE 9003
#ENTRYPOINT ["java", "-jar", "app.jar"]