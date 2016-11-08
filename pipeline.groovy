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
            sh "cp irunninglog.jar ${dest}/"
            stash name: "int-shaded-jar", includes: "${dest}/**"
        }
        dir ('irunninglog-freemarker/target/generated-sources/fmpp/int') {
            sh "mkdir ${dest}"
            sh "cp application.properties ${dest}/"
            stash name: "int-config", includes: "${dest}/**"
        }
        def destProd = pom.version
        dir ('irunninglog-main/target') {
            sh "mkdir ${destProd}"
            sh "cp irunninglog.jar ${destProd}/"
            stash name: "prod-shaded-jar", includes: "${destProd}/**"
        }
        dir ('irunninglog-freemarker/target/generated-sources/fmpp/prod') {
            sh "mkdir ${destProd}"
            sh "cp application.properties ${destProd}/"
            stash name: "prod-config", includes: "${destProd}/**"
        }
        deleteDir()
    }

    stage('Install (INT)') {
        unstash name: "int-shaded-jar"
        unstash name: "int-config"
        sh "ls -al"
        sh "rsync -av . ${env.DEPOT_LOCAL}"
        deleteDir()
    }

    stage('Clean (INT)') {
        sh "find ${env.DEPOT_LOCAL} -mtime +30 | xargs rm -rf {} \\;"
    }

    stage('Install (PROD)') {
        unstash name: "prod-shaded-jar"
        unstash name: "prod-config"
        sh "ls -al"
        //sh "rsync -av . ${env.DEPOT_LOCAL}"
        deleteDir()
    }
}
