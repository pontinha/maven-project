pipeline {
    agent any

    parameters {
        string(name: 'instances_dir', defaultValue: '/home/pontinha/Development/tools/tomcat/instances', description: 'Instances directory')
        string(name: 'tomcat_stag', defaultValue: '1', description: 'Staging server')
        string(name: 'tomcat_prod', defaultValue: '2', description: 'Production server')
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
                        sh "cp **/target/*.war ${params.instances_dir}/${params.tomcat_stag}/webapps/"
                    }
                }
                stage('Deploy to production') {
                    steps {
                        sh "cp **/target/*.war ${params.instances_dir}/${params.tomcat_prod}/webapps/"
                    }
                }
            }
        }
    }
}