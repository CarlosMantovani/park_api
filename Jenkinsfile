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

        stage('Análise Sonar') {
            environment {
                scannerHome = tool 'SONAR_SCANNER'
                SONAR_SCANNER_OPTS = '--add-opens java.base/java.lang=ALL-UNNAMED'
            }
            steps {
                withSonarQubeEnv('SONAR_LOCAL') {
                    bat "\"%scannerHome%\\bin\\sonar-scanner\" -e -Dsonar.projectKey=Analise_parkApi -Dsonar.host.url=http://localhost:9000 -Dsonar.login=843f351375288e659cf7c6348fbab3afc3b748d2 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**/application.java"
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
                    echo "Construindo imagem: ${IMAGE_NAME}:${BUILD_NUMBER}"
                    docker.build("${IMAGE_NAME}:${BUILD_NUMBER}", "--file Dockerfile .")
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
