def call()
{
    pipeline {

        agent {
            label 'workstation'

        }

        stages{

            stage('compile/build'){
                steps
                        {
                            echo 'compile'
                        }
            }

            stage('unit Tests'){
                steps
                        {
                            echo 'unit tests'
                        }
            }
            stage('compile/build'){
                steps
                        {
                            echo "compile"
                        }
            }
            stage('quality control'){
                steps
                        {
                            echo "quality control"
                        }
            }

            stage('upload code to centralised place'){
                steps
                        {
                            echo "upload code to centralised place"
                        }
            }




        }
        post {
            always{
                echo "sending email"
            }
        }
    }

}