pipeline {
    agent any
    stages {
      stage('jmeter test') {
        steps {
            sh 'docker run --name jmeter_base vovan4/jmeter_base:latest'
            sleep 20
            sh "docker exec jmeter_base sh 'cd bin && git clone https://github.com/praveenkrjha93/JMeter-Sample.git'"
            sh 'sh jmeter -n -t JMeter-Sample/JMeter-load-test.jmx -JThreadNumber=10 -JRampUpPeriod=1 -Jiterations=10 -l results.csv -e -o /result_output'
        }
      }
    }
    post {
        always {
            cleanWs()
        }
    }
}
