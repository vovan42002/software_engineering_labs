pipeline {
    agent any
    stages {
      stage('jmeter test') {
        agent {
          dockerfile {
            filename 'lab3/Dockerfile.jmeter'
          }
        }
        steps {
          sh 'jmeter -n -t JMeter-Sample/JMeter-load-test.jmx -JThreadNumber=10 -JRampUpPeriod=1 -Jiterations=10 -l results.csv -e -o /result_output'
        }
      }_
        stage('Install dependencies') {
            agent {
              docker {
                image 'maven:latest'
              }
            }
            steps {
                sh 'cd lab3 && mvn install:install-file -Dfile=libs/Simulation2022.jar -DgroupId=com.simulation -DartifactId=simulation -Dversion=1.0.0 -Dpackaging=jar'
                sh 'cd lab3 && mvn clean compile assembly:single'
            }
        }
        stage('Check existing runnable jar') {
            steps {
                sh 'ls lab3/target'
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}
