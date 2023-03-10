def call() {
    try {
        node('workstation') {

            stage('CleanUp') {
                cleanWs()
                git branch: 'main', url: "https://github.com/mobiqa/frontend"
            }

            stage('Compile/Build') {
                common.compile()
            }

            stage('Unit Tests') {
                common.unittests()
            }

            stage('Quality Control') {
                SONAR_PASS = sh ( script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.pass  --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true)
                SONAR_USER = sh ( script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.user  --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true)
                        //wrap([$class: 'MaskPasswordsBuildWrapper', varPasswordPairs: [[password: "${SONAR_PASS}", var: 'SECRET']]])
                        {
                            sh "sonar-scanner -Dsonar.host.url=http://172.31.7.166:9000 -Dsonar.login=${SONAR_USER} -Dsonar.password=${SONAR_PASS} -Dsonar.projectKey=frontend"
                        }
            }

            stage('Upload Code to Centralized Place') {
                echo 'Upload'
            }




        }

    } catch(Exception e) {
        common.email("Failed")
    }
}
