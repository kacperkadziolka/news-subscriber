pipeline {
    agent any
    environment {
        AWS_ACCOUNT_ID="047645927285"
        AWS_DEFAULT_REGION="eu-central-1"
        IMAGE_REPO_NAME="ecrrepositorytest"
        IMAGE_TAG="${env.BUILD_ID}"
        REPOSITORY_URI = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${IMAGE_REPO_NAME}"
        CLUSTER_NAME="defaultCluster"
	    SERVICE_NAME="springboot-container-service"
	    TASK_DEFINITION_NAME="first-run-task-definition"
	    DESIRED_COUNT="1"
	    registryCredential = "demo-admin-user"
    }

    stages {

        // Building Docker images
        stage('Building image') {
            steps{
                script {
                    dockerImage = docker.build "${IMAGE_REPO_NAME}:${IMAGE_TAG}"
                }
            }
        }

        // Uploading Docker images into AWS ECR
        stage('Pushing to ECR') {
            steps{
                script {
                    docker.withRegistry("https://" + REPOSITORY_URI, "ecr:${AWS_DEFAULT_REGION}:" + registryCredential) {
                        dockerImage.push()
                    }
                }
            }
        }

        stage('Deploy to ECS') {
            steps {
                withAWS(credentials: registryCredential, region: "${AWS_DEFAULT_REGION}") {
                    script {
			            sh './script.sh'
                    }
                }
            }
        }
    }
}