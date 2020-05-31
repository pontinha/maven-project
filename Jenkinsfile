pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
            post {
                success {
                    echo 'Now archiving...'

                }
            }
        }

        stage('Deploy') {
            steps {
                echo 'Code deployed.'
                archiveArtifacts artifacts: '**/target/*.war'
            }
        }
    }
}