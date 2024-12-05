FROM openjdk:17-jdk
WORKDIR /app
COPY target/HomeworkCICD-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]



## Используем официальный образ Jenkins LTS
#FROM jenkins/jenkins:lts
#
## Устанавливаем root-доступ для установки пакетов
#USER root
#
## Устанавливаем зависимости
#RUN apt-get update && apt-get install -y \
#    maven \
#    docker.io \
#    && apt-get clean && rm -rf /var/lib/apt/lists/*
#
## Добавляем пользователя Jenkins в группу docker
#RUN usermod -aG docker jenkins
#
## Устанавливаем переменные окружения для Maven
#ENV MAVEN_HOME=/usr/share/maven \
#    MAVEN_CONFIG=/var/maven/.m2
#
## Создаем директорию для Maven (для кеширования зависимостей)
#RUN mkdir -p /var/maven/.m2 && chown -R jenkins:jenkins /var/maven
#
## Меняем пользователя обратно на Jenkins
#USER jenkins
#
## Вывод версии Maven для проверки
#RUN mvn --version
