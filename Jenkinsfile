pipeline {
    agent any

    environment {
       IMAGE_NAME = "ghcr.io/carlosmantovani/park_api"
       REPO_URL = "https://github.com/CarlosMantovani/park_api.git"
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
                    docker.build("${IMAGE_NAME}:${BUILD_NUMBER}", "--file Dockerfile .")
                }
            }
        }
        stage('Push para GitHub Packages') {
            steps {
                script {
                    docker.withRegistry('https://github.com/login?return_to=https%3A%2F%2Fgithub.com%2Ffeatures%2Factions', 'github_login') {
                        docker.image("${IMAGE_NAME}:${BUILD_NUMBER}").push()
                    }
                }
            }
        }
    }
}
