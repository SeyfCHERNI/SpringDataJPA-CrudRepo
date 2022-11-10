pipeline {
    agent any

environment {
   PATH = "/usr/share/man/man1/mvn.1.gz:$PATH"

}
    stages {
        stage('Git') {
            steps {
               git branch: 'amir', url: 'https://github.com/devCyberops/SpringDataJPA-CrudRepo.git'
            }
        }
            stage('MVN Clean') {
            steps {
                sh 'mvn clean'
                  }
        }
        stage('MVN Compile'){
            steps {
                sh 'mvn compile'
            }
        }
        stage('MVN Build'){
            steps {
                sh 'mvn install'
            }
        }
        stage ('MVN SonarQube') {
		steps {
			
                sh  "mvn sonar:sonar -Dsonar.login=ee074ba54a7030fc4acf6117d9b3a5490e12febd"
	
		}
	}
              stage("nexus deploy"){
               steps{
                       sh 'mvn clean deploy -DskipTests'
                       sh'mvn clean deploy -Dmaven.test.skip=true -Dresume=false'
               }
          }
          
          stage("Test JUnit - Mockito"){
                steps {
                            sh 'mvn test'
                }
          }
          stage('Docker Build and Push') {
       steps {
         withDockerRegistry([credentialsId: "Docker-Hub-AmirTrigui", url: ""]) {
           sh 'printenv'
           sh 'sudo docker build -t likeaboos/ci:latest .'
           sh 'docker push likeaboos/ci:latest '
         }
       }
     }


stage('Docker Compose') {
       steps {
               sh 'docker-compose up --d --force-recreate '
       }
     }
     
     post{

            success {
                mail to: "amirtrigui8@gmail.com",
                body: "${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n, More info at: ${env.BUILD_URL}",
                from: "amirtrigui8@gmail.com",
                subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}"
            }

            failure{
                mail to: "amirtrigui8@gmail.com",
                subject: "jenkins build:${currentBuild.currentResult}: ${env.JOB_NAME}",
                from: "amirtrigui8@gmail.com",
                body: "${currentBuild.currentResult}: Job ${env.JOB_NAME}\nMore Info can be found here: ${env.BUILD_URL}"
            }

            changed{
                mail to: "amirtrigui8@gmail.com",
                subject: "jenkins build:${currentBuild.currentResult}: ${env.JOB_NAME}",
                from: "amirtrigui8@gmail.com",
                body: "${currentBuild.currentResult}: Job ${env.JOB_NAME}\nMore Info can be found here: ${env.BUILD_URL}"
            }
    }
}
