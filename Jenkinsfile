pipeline {
    agent any

    parameters {
        string(name: 'tomcat_stag', defaultValue: 'localhost:8081', description: 'Staging server')
        string(name: 'tomcat_prod', defaultValue: 'localhost:8082', description: 'Production server')
    }

    triggers {
        pollSCM('* * * * *')
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
            post {
                success {
                    echo 'Now archiving...'
                    archiveArtifacts artifacts: '**/target/*.war'
                }
            }
        }

        stage('Deployments') {
            parallel {
                stage('Deploy to staging') {
                    steps {
                        sh "cp **/target/*.war ${params.tomcat_stag}:/home/pontinha/Development/tools/tomcat/instances/1/webapps"
                    }
                }
                stage('Deploy to production') {
                    steps {
                        sh "cp **/target/*.war ${params.tomcat_prod}:/home/pontinha/Development/tools/tomcat/instances/1/webapps"
                    }
                }
            }
        }
    }
}