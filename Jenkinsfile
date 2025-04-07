pipeline {
    agent any

    environment {
        IMAGE_NAME = "ghcr.io/carlosmantovani/park_api"
        REPO_URL = "https://github.com/CarlosMantovani/park_api.git"
        REGISTRY = "https://ghcr.io"
        CREDENTIALS_ID = "github-packages-token" // ID da credencial Jenkins (PAT)
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
                    echo "Construindo imagem: ${IMAGE_NAME}:${BUILD_NUMBER}"
                    image = docker.build("${IMAGE_NAME}:${BUILD_NUMBER}", "--file Dockerfile .")
                    bat "docker images | findstr park_api"
                }
            }
        }

        stage('Push para GitHub Packages') {
            steps {
                script {
                    echo " Iniciando push da imagem: ${IMAGE_NAME}:${BUILD_NUMBER}"

                    // Login e push usando credencial Jenkins com PAT
                    docker.withRegistry("${REGISTRY}", "${CREDENTIALS_ID}") {
                        docker.image("${IMAGE_NAME}:${BUILD_NUMBER}").push()
                    }

                    echo "Push realizado com sucesso: ${IMAGE_NAME}:${BUILD_NUMBER}"
                }
            }
        }
    }
}
