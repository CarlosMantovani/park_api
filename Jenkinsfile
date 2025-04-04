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
        stage('Análise Sonar') {
            environment {
                scannerHome = tool 'SONAR_SCANNER'
                SONAR_SCANNER_OPTS = '--add-opens java.base/java.lang=ALL-UNNAMED'
            }
            steps {
                withSonarQubeEnv('SONAR_LOCAL') {
                    bat "\"${scannerHome}/bin/sonar-scanner\" -e -Dsonar.projectKey=Analise_parkApi -Dsonar.host.url=http://localhost:9000 -Dsonar.login=843f351375288e659cf7c6348fbab3afc3b748d2 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**/application.java"
                }
            }
        }
        stage('Quality Gate') {
            steps {
                sleep(50)
                timeout(time: 1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
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
                    docker.withRegistry('https://hub.docker.com/repositories', 'DockerHub') {
                        def image = docker.build("${DOCKER_HUB_REPO}:${BUILD_NUMBER}", "--file Dockerfile .")
                        image.push()
                    }
                }
            }
        }
    }
}
