pipeline {
    agent any

    environment {
        IMAGE_NAME = "park_api"
        CONTAINER_NAME = "park_api"
        REPO_URL = "https://github.com/CarlosMantovani/park_api.git"
        DOCKER_HUB_USER = "seu_usuario_dockerhub"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: "${REPO_URL}"
            }
        }
    }
}
