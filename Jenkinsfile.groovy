pipeline {
    agent any

    environment {
        REPO_URL = 'https://github.com/zhernakov14/homework-ci-cd.git'
        IMAGE_NAME = 'homework-ci-cd' // Имя Docker образа
        NEXUS_REPO = 'homework-cicd-repo' // Имя репозитория Nexus
    }


    stages {
        stage('Клонирование репозитория') {
            steps {
                git "${REPO_URL}" // Замените на URL вашего репозитория
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
                sh "docker run -d --network cicd_network -p 8080:8080 ${IMAGE_NAME}"
            }
        }

//        stage('Загрузка JAR в Nexus') {
//            steps {
//                script {
//                    def server = Artifactory.server 'nexus-server' // Определите учетные данные сервера Nexus в Jenkins
//                    def uploadSpec = """{
//                        "files": [
//                            {
//                                "pattern": "target/*.jar", // Путь к вашему JAR файлу
//                                "target": "${NEXUS_REPO}/" // Использование переменной NEXUS_REPO
//                            }
//                        ]
//                    }"""
//                    server.upload(uploadSpec)
//                }
//            }
//        }

        stage('Upload JAR to Nexus') {
            steps {
                // Загружаем JAR в Nexus
                script {
                    def jarFile = 'target/HomeworkCICD-0.0.1-SNAPSHOT.jar' // Укажите путь к вашему JAR файлу
                    def nexusUrl = 'http://nexus:8081/repository/homework-cicd-repo/'
                    def nexusUser = 'admin'
                    def nexusPassword = 'admin'

                    sh """
                        curl -v -u ${nexusUser}:${nexusPassword} --upload-file ${jarFile} ${nexusUrl}
                    """
                }
            }
        }
    }
}