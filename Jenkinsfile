pipeline {
    agent any

    environment {
        IMAGE_NAME = "park_api"
        CONTAINER_NAME = "park_api"
        REPO_URL = "https://github.com/CarlosMantovani/park_api.git"
        DOCKER_HUB_USER = "carlosdev937"
        DOCKER_HUB_REPO = "${DOCKER_HUB_USER}/park_api"
    }
    
    stages {
        stage('Build Projeto') {
            steps {
                bat 'mvn clean package -DskipTests=true'
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    // Constrói a imagem e a armazena na variável 'dockerImage'
                    dockerImage = docker.build("${DOCKER_HUB_REPO}:${BUILD_NUMBER}", "--file Dockerfile .")
                }
            }
        }
        stage('Push Docker Image') {
            steps {
                script {
                    // Autentica no Docker Hub e dá push na imagem construída
                    docker.withRegistry('https://registry.hub.docker.com', 'DockerHub') {
                        dockerImage.push()          // Push com a tag do BUILD_NUMBER
                      
                    }
                }
            }
        }
        stage('Deploy Docker Image') {
            steps {
                script {
                    // Para e remove o container atual (se existir)
                    bat "docker stop ${CONTAINER_NAME} || exit 0"
                    bat "docker rm ${CONTAINER_NAME} || exit 0"
                    // Executa o novo container com a imagem atualizada
                    bat "docker run -d --name ${CONTAINER_NAME} -p 8080:8080 --network park_network ${DOCKER_HUB_REPO}:${BUILD_NUMBER}"
                }
            }
        }
    }
}
