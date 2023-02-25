# roboshop-jenkins-shared-library

Create a folder vars, start writing the groovy scripts under var folder.
say for ex: ci.groovy...test.groovy
ci.groovy: In ci.groovy copy the pipeline script and add function def call() { pipeline code }
once it is done call the ci.groovy file from Jenkins file using

 @Library('roboshop') _
 ci()
Here Roboshop is the file name we gave for the Library Name in Configure system
http://44.195.20.66:8080/manage/configure

Project Repository: Add the Repo link https://github.com/mobiqa/roboshop-jenkins-shared-library

Now from here it picks the jenkins file from the repository





