pipeline {
    agent any

    triggers {
        pollSCM('H/2 * * * *') // Check Git every 2 mins
    }

    environment {
        IMAGE_NAME = "raft-service"
        CONTAINER_NAME = "raft-service"
    }

    stages {
        stage('Clone') {
            steps {
                checkout scm
            }
        }

        stage('Build JAR') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat "docker build -t %IMAGE_NAME% ."
            }
        }

        stage('Run Docker Container') {
            steps {
                bat "docker rm -f %CONTAINER_NAME% || exit 0"
                bat "docker run -d -p 8080:8080 --name %CONTAINER_NAME% %IMAGE_NAME%"
            }
        }
    }
}
