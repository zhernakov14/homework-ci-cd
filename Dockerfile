FROM openjdk:17-jdk
WORKDIR /app
COPY target/HomeworkCICD-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9003
ENTRYPOINT ["java", "-jar", "app.jar"]


#FROM jenkins/jenkins:lts
#
## Устанавливаем Maven
#USER root
#RUN apt-get update && \
#    apt-get install -y maven && \
#    apt-get clean && \
#    rm -rf /var/lib/apt/lists/*
#
#USER jenkins


#FROM jenkins/jenkins:lts
#USER root
#RUN apt-get update && apt-get install -y maven
#RUN apt-get update && apt-get install -y docker.io