pipeline {
    agent any

    parameters {
//        string(name: 'instances_dir', defaultValue: '/home/pontinha/Development/tools/tomcat/instances', description: 'Instances directory')
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
                        sh """
                            CURL_RESPONSE=\$(curl -u jenkins:jenkins -T **/target/*.war \"http://${params.tomcat_stag}/manager/text/deploy?path=/&update=true\")    
                            echo "\$CURL_RESPONSE"
                            
                            if [[ \$CURL_RESPONSE == *"FAIL"* ]]; then
                              exit 1
                            else
                              exit 0
                            fi
                        """
//                        sh "curl 'http://jenkins:jenkins@${params.tomcat_stag}/manager/text/deploy?war=file:**/target/*.war&update=true'"
//                        node('Deploy') {
//                            withCredentials([usernameColonPassword(credentialsId: 'jenkins', variable: 'PASSWORD')]) {
////                                sh "curl -v -u jenkins:$PASSWORD -T **/target/*.war 'http://${params.tomcat_stag}/manager/text/deploy?war=/$CONTEX_NAME&update=true'"
//                                sh "curl --upload-file **/target/*.war 'http://jenkins:$PASSWORD:${params.tomcat_stag}/manager/text/deploy?update=true'"
//                            }
//                        }
                    }
                }
                stage('Deploy to production') {
                    steps {
                        sh """
                            CURL_RESPONSE=\$(curl \"http://jenkins:jenkins@${params.tomcat_prod}/manager/text/deploy?war=**/target/*.war&update=true\")    
                            echo "\$CURL_RESPONSE"
                            
                            if [[ \$CURL_RESPONSE == *"FAIL"* ]]; then
                              exit 1
                            else
                              exit 0
                            fi
                        """
                    }
                }
            }
        }
    }
}