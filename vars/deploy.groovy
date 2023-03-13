def call() {

  pipeline {

    agent any

    parameters {
      string(name: 'APP_ENV', defaultValue: '', description: 'Enter Env like dev or prod')
      choice(name: 'COMPONENT', defaultValue: '', description: 'Enter component name')
      choice(name: 'APP_VERSION', defaultValue: '', description: 'Enter application version to deploy')
    }

    stages {

      stage('Run Deployment') {
        steps {
          sh '''
            aws ssm put-parameter --name "${APP_ENV}.${COMPONENT}.APP_VERSION" --type "String" --value "${APP_VERSION}" --overwrite
          '''
        }
      }

    }

  }

}