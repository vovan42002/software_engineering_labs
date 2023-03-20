pipeline {
    agent any
    stages {
      stage('jmeter test') {
        steps {
            sh 'docker run -d -i -t --name jmeter_base vovan4/jmeter_base:latest'
            sleep 5
            sh "docker exec jmeter_base 'cd bin && git clone https://github.com/praveenkrjha93/JMeter-Sample.git && sleep 10 && sh jmeter -n -t JMeter-Sample/JMeter-load-test.jmx -JThreadNumber=10 -JRampUpPeriod=1 -Jiterations=10 -l results.csv -e -o /result_output'"
        }
      }
    }
    post {
        always {
            cleanWs()
        }
    }
}
