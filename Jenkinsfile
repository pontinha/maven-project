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
                        sh """
                            whoami
                            ls -l **/target/*.war
                            cp **/target/*.war ${params.instances_dir}/${params.tomcat_stag}/webapps/
                            ls -l ${params.instances_dir}/${params.tomcat_stag}/webapps/*.war
                        """
                    }
                }
                stage('Deploy to production') {
                    steps {
                        sh """
                            whoami
                            ls -l **/target/*.war
                            cp **/target/*.war ${params.instances_dir}/${params.tomcat_prod}/webapps/
                            ls -l ${params.instances_dir}/${params.tomcat_prod}/webapps/*.war
                        """
                    }
                }
            }
        }
    }
}