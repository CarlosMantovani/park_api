pipeline {
    agent any

    environment {
        IMAGE_NAME = "park_api"
        CONTAINER_NAME = "park_api"
        DOCKER_HUB_REPO = "seu_usuario_dockerhub/park_api"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/CarlosMantovani/park_api.git'
            }
        }
        stage('Build Projeto') {
            steps {
                bat 'mvn clean package -DskipTests=true'
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    def image = docker.build("${DOCKER_HUB_REPO}:${BUILD_NUMBER}", "--file Dockerfile .")
                }
            }
        }
        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'dockerhub-credentials') {
                        def image = docker.build("${DOCKER_HUB_REPO}:${BUILD_NUMBER}", "--file Dockerfile .")
                        image.push()
                    }
                }
            }
        }
        stage('Deploy Docker Image') {
            steps {
                script {
                    // Parar e remover o container atual (se necessário)
                    sh "docker stop ${CONTAINER_NAME} || true"
                    sh "docker rm ${CONTAINER_NAME} || true"

                    // Rodar o novo container com a nova imagem
                    sh "docker run -d --name ${CONTAINER_NAME} -p 8080:8080 --network park_network ${DOCKER_HUB_REPO}:${BUILD_NUMBER}"
                }
            }
        }
    }
}
