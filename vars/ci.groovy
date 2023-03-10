def call() {

    if(!env.SONAR_EXTRA_OPTS) {
        env.SONAR_EXTRA_OPTS = " "
    }
    try {
        node('workstation') {

            stage('Checkout') {
                cleanWs()
                git branch: 'main', url: "https://github.com/mobiqa/${component}"
            }

            stage('Compile/Build') {
                common.compile()
            }

            stage('Unit Tests') {
                common.unittests()
            }

            stage('Quality Control') {
//                SONAR_PASS = sh ( script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.pass  --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
//                SONAR_USER = sh ( script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.user  --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
//                wrap([$class: 'MaskPasswordsBuildWrapper', varPasswordPairs: [[password: "${SONAR_PASS}", var: 'SECRET']]])

                    sh "sonar-scanner -Dsonar.host.url=http://172.31.7.166:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.projectKey=${component} -Dsonar.java.binaries=./target -Dsonar.qualitygate.wait=true ${SONAR_EXTRA_OPTS}"

            }

            stage('Upload Code to Centralized Place') {
                echo 'Upload'
            }

        }

    } catch(Exception e) {
        common.email("Failed")
    }
}