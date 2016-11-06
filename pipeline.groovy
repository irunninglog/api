node {
    def mvnHome = tool 'M3'
    def m3 = "${mvnHome}/bin/mvn"

    stage('Validate') {
        git url: 'https://github.com/allan-lewis/irunninglog-server.git/', credentialsId: '02de7a3c-0ad2-470c-86c2-a1d805fd1471'
        sh "${m3} -B validate"
    }

    stage('Build') {
        sh "${m3} -B clean install"
    }

    stage('Stash') {
        def pom = readMavenPom()
        def dest = pom.version + "-" + sh(returnStdout: true, script: 'date +%Y%m%d%H%M%S').trim()
        dir ('irunninglog-main/target') {
            sh "mkdir ${dest}"
            sh "mv irunninglog.jar ${dest}/"
            stash name: "shaded-jar", includes: "${dest}/**"
        }
        deleteDir()
    }

    stage('Install (Local)') {
        unstash name: "shaded-jar"
        sh "ls -al"
        sh "rsync -av . ${env.DEPOT_LOCAL}"
    }

    stage('Clean (Local)') {
        sh "find ${env.DEPOT_LOCAL} -mtime +30 | xargs rm -rf {} \\;"
    }
}