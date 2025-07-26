pipeline {
    agent any

    triggers {
        pollSCM('H/2 * * * *') // every 2 minutes
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
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t $IMAGE_NAME ."
            }
        }

        stage('Run Docker Container') {
            steps {
                sh "docker rm -f $CONTAINER_NAME || true"
                sh "docker run -d -p 8080:8080 --name $CONTAINER_NAME $IMAGE_NAME"
            }
        }
    }
}
