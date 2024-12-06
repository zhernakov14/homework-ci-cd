pipeline {
    agent any

    options {
        timestamps()
    }

    environment {
        REPO_URL = 'https://github.com/zhernakov14/homework-ci-cd.git'
        IMAGE_NAME = 'homework-ci-cd' // Имя Docker образа
        NEXUS_REPO = 'homework-cicd-repo' // Имя репозитория Nexus
    }


    stages {
        stage('Клонирование репозитория') {
            steps {
//                git "${REPO_URL}" // Замените на URL вашего репозитория
                checkout scm
            }
        }

        stage('Сборка JAR') {
            steps {
                sh 'mvn clean package' // Настройте в зависимости от вашего инструмента сборки (Maven/Gradle)
            }
        }

        stage('Сборка Docker образа') {
            steps {
                sh "docker build -t ${IMAGE_NAME} ."
            }
        }

        stage('Запуск Docker контейнера') {
            steps {
                sh """
                    docker rm -f my-test-container || true
                    docker run -d -p 8083:8080 --network cicd_network --name my-test-container ${IMAGE_NAME}
                """
            }
        }

        stage('Upload JAR to Nexus') {
            steps {
                script {
                    // Настройки Nexus
                    def jarFile = 'target/HomeworkCICD-0.0.1-SNAPSHOT.jar' // Путь к JAR файлу
                    def groupId = 'com.example' // Ваш groupId
                    def artifactId = 'HomeworkCICD' // Ваш artifactId
                    def version = '0.0.1-SNAPSHOT' // Версия артефакта
                    def nexusUrl = 'http://nexus:8081/repository/homework-cicd-repo'
                    def nexusUser = 'admin'
                    def nexusPassword = 'admin'

                    // Формируем путь для Maven
                    def mavenPath = "${groupId.replace('.', '/')}/${artifactId}/${version}/${artifactId}-${version}.jar" as Object

                    // Загружаем JAR в Nexus
                    sh """
                        curl -v -u ${nexusUser}:${nexusPassword} \
                        --upload-file ${jarFile} \
                        ${nexusUrl}/${mavenPath}
                    """
                }
            }
        }
    }

    post {
        always {
            cleanWs() // Очистка рабочего пространства после выполнения пайплайна
        }
    }

}