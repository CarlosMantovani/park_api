pipeline {
    agent any

    environment {
        IMAGE_NAME = "park_api"
        CONTAINER_NAME = "park_api"
        REPO_URL = "https://github.com/CarlosMantovani/park_api.git"
        DOCKER_HUB_USER = "carlosdev937"
        DOCKER_HUB_REPO = "${DOCKER_HUB_USER}/park_api"  // Definindo a variável aqui
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: "${REPO_URL}"
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
                    // Constrói a imagem e armazena na variável global 'dockerImage'
                    dockerImage = docker.build("${DOCKER_HUB_REPO}:${BUILD_NUMBER}", "--file Dockerfile .")
                }
            }
        }
        stage('Push Docker Image') {
            steps {
                script {
                    // Usa o docker.withRegistry para autenticar e dar push na imagem construída
                    docker.withRegistry('https://registry.hub.docker.com', 'dockerhub-credentials') {
                        dockerImage.push()          // Faz push com a tag do BUILD_NUMBER
                        dockerImage.push('latest')  // Opcional: também faz push com a tag 'latest'
                    }
                }
            }
        }
    }
}
