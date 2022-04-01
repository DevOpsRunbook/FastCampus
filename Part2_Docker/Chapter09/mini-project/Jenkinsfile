// Common
def mainDir="Chapter09/mini-project"
def region="<AWS Region>"
def deployHost="<Deploy VM Private IP>"

// Repository Info
def repository="<Image Repository Name>"
def baseImageTag="update-base-image"
def appImageTag="update-app-image"

// Nexus
def nexusUrl="<Nexus Private IP>:5000"
def nexusProxyUrl="<Nexus Private IP>:5001"

// AWS ECR
def ecrLoginHelper="docker-credential-ecr-login"
def ecrUrl="<AWS ECR URL>"


pipeline {
    agent any

    stages {
        stage('Checkout and Pull Codes from Github Repository'){
            steps{
                checkout scm
            }
        }
        stage('Build Base Image by Docker') {
            steps {
                sh """
                cd ${mainDir}/base-image
                echo '<Nexus repo PW>' | docker login -u '<Nexus repo ID>' --password-stdin ${nexusUrl}
                echo '<Nexus repo PW>' | docker login -u '<Nexus repo ID>' --password-stdin ${nexusProxyUrl}
                docker build -t ${nexusUrl}/${repository}:${baseImageTag} .
                docker push ${nexusUrl}/${repository}:${baseImageTag}
                """
            }
        }           
        stage('Scan Static Codes Quality by Jacoco and SonarQube') {
            steps {
                sh """
                cd ${mainDir}
                ./gradlew jacocoTestCoverageVerification --info
                ./gradlew jacocoTestReport --info
                ./gradlew sonarqube --info
                """
            }
        }        
        stage('Clean and Build Codes by Gradle') {
            steps {
                sh """
                cd ${mainDir}
                ./gradlew clean build --info
                """
            }
        }
        stage('Build Docker Image by Jib & Push to Nexus Custom Repository') {
            steps {
                sh """
                    cd ${mainDir}
                    ./gradlew jib -Djib.to.image=${nexusUrl}/${repository}:${appImageTag} -DsendCredentialsOverHttp=true -Djib.console='plain'
                """
            }
        }
        stage('Build Docker Image by Jib & Push to AWS ECR Repository') {
            steps {
                withAWS(region:"${region}", credentials:"aws-key") {
                    ecrLogin()
                    sh """
                        curl -O https://amazon-ecr-credential-helper-releases.s3.us-east-2.amazonaws.com/0.4.0/linux-amd64/${ecrLoginHelper}
                        chmod +x ${ecrLoginHelper}
                        mv ${ecrLoginHelper} /usr/local/bin/
                        cd ${mainDir}
                        ./gradlew jib -Djib.to.image=${ecrUrl}/${repository}:${appImageTag} -Djib.console='plain'
                    """
                }
            }
        }        
        stage('Scan Security CVE at Clair Scanner') {
            steps {
                script {
                    try {
                        jenkins_ip = sh(script: "docker inspect -f '{{ .NetworkSettings.IPAddress }}' jenkins", returnStdout: true).trim()
                        clair_ip = sh(script: "docker inspect -f '{{ .NetworkSettings.IPAddress }}' clair", returnStdout: true).trim()
                        sh """
                            apt update
                            apt install -y wget
                            docker pull ${nexusUrl}/${repository}:${appImageTag}
                            wget https://github.com/arminc/clair-scanner/releases/download/v12/clair-scanner_linux_amd64
                            chmod +x clair-scanner_linux_amd64
                            mv clair-scanner_linux_amd64 /usr/local/bin/clair-scanner
                        """
                        sh "clair-scanner --ip ${jenkins_ip} --clair='http://${clair_ip}:6060' --log='clair.log' \
                                --report='report.txt' ${nexusUrl}/${repository}:${appImageTag}"
                    } catch (err) {
                        echo err.getMessage()
                    }
                }
                echo currentBuild.result
            }
        }
        stage('Deploy Nexus Repository to AWS EC2 VM'){
            steps{
                sshagent(credentials : ["deploy-key"]) {
                    sh "ssh -o StrictHostKeyChecking=no ubuntu@${deployHost} \
                      'echo \"<Nexus repo PW>\" | docker login -u <Nexus repo ID> --password-stdin ${nexusUrl}; \
                      docker run -d -p 81:8080 -t ${nexusUrl}/${repository}:${appImageTag};'"
                }
            }
        }
        stage('Deploy AWS ECR Repository to AWS EC2 VM'){
            steps{
                sshagent(credentials : ["deploy-key"]) {
                    sh "ssh -o StrictHostKeyChecking=no ubuntu@${deployHost} \
                     'aws ecr get-login-password --region ${region} | docker login --username AWS --password-stdin ${ecrUrl}/${repository}; \
                      docker run -d -p 82:8080 -t ${ecrUrl}/${repository}:${appImageTag};'"
                }
            }
        }
    }
}
