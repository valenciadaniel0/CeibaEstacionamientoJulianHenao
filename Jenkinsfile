import java.text.SimpleDateFormat

node('Slave_Induccion') {

    try{
        def dateFormat = new SimpleDateFormat("dd-MM-yyy")
        def date = new Date()
        String dateNow = dateFormat.format(date)

        // banderas de prueba
        def testDesarrollo = true
        def testQA = true
        def testProduccion = true

        //Tools
        env.JAVA_HOME="${tool 'JDK8_Centos'}"
        env.GRADLE="${tool 'Gradle4.5_Centos'}"
        env.PATH="${env.JAVA_HOME}/bin:${env.PATH}"
        //env.sonarHome= tool name: 'SonarScanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
        
        // variables de ambiente
        env.directorio = ""
        env.versionamiento = "${dateNow}.${BUILD_NUMBER}"
        env.targeString = ""
        
        // INICIO STAGES

        stage('Checkout') {
            checkout()
        }
        
        stage('Clean') {
            dir("${env.directorio}"){
                bat './gradlew clean'
            }
        }
        
        stage('Compilacion') {
            dir("${env.directorio}"){
                bat './gradlew compileJava'
            }
        }

        stage('Unit Test') {
            dir("${env.directorio}"){
                bat './gradlew test'
                junit '**/build/test-results/test/*.xml'
                jacoco classPattern:'**/build/classes/java', sourcePattern:'**/src/main/java', execPattern:'**/build/jacoco/*.exec'
            }
        }
   /*     
        stage('Sonar'){
            withSonarQubeEnv('Sonar') {
                bat "${sonarHome}/bin/sonar-scanner -Dproject.settings=./${env.directorio}/sonar-project.properties"
            }  
        }
        
        stage("Quality Gate"){
            timeout(time: 1, unit: 'HOURS') {
                def qg = waitForQualityGate()
                if (qg.status != 'OK') {
                    error "Pipeline abortado porque el quality gate del análisis del sonar no es OK: ${qg.status}"
                }
            }
        }
*/
        stage('Build') {
            dir("${env.directorio}"){
                bat './gradlew build -x test'
            }
        }
        
        stage('PUBLISH ALPHA') {        
            publicarArtefacto("alpha")        
        }

        stage('DEPLOY DLLO') {            
            descargarUltimaVersionAlphaAlJenkins()
            dir("artefactos/alpha/"){
                    bat 'dir'
                }
            //desplegar en dllo
            deploy()
            
        }

        stage('TESTING DLLO') {        
            try{
                if(testDesarrollo==false){
                    error("fallaron los test en el ambiente de desarrollo")
                }
            }catch(err) {
                println(err.getMessage());
                throw err
            }        
        }

        stage('PUBLISH BETA') {        
            publicarArtefacto("beta")        
        }

        stage('DEPLOY QA') {        
            // Desplegar en  QA  
        }

        stage('TESTING QA') {        
            try{
                if(testQA==false){
                    error("fallaron los test en el ambiente de QA")
                }
            }catch(err) {
                println(err.getMessage());
                throw err
            }        
        }

        stage('PUBLISH RC') {        
            publicarArtefacto("release-candidate")        
        }

        stage('DEPLOY PRODUCCION') {        
            // Desplegar en  QA  
        }

        stage('TESTING PRODUCCION') {        
            try{
                if(testProduccion==false){
                    error("fallaron los test en el ambiente de Producción")
                }
            }catch(err) {
                descargarVersionEstableReleaseAlJenkins()
                println(err.getMessage());
                dir("artefactos/release/"){
                    echo "estos son los archivos en artefactos/release en jenkins"
                    bat 'ls -al'
                }
                // enviar notificacion warning
                throw err
            }        
        }

        stage('PUBLISH RELEASE') {        
            publicarArtefacto("release")
            publicarArtefactoEstableRelease()       
        }

        stage('STAGE END PIPELINE') {        
            eliminarCarpetaArtefactosEnJenkins()
        }
        
        // fin stages

    }catch(err){
        echo "Hubo un error en el pipeline"

    }finally{
        notificar()
    }
    
    
}// fin node

def checkout(){
    checkout([
                $class: 'GitSCM',
                branches: [[
                    name: '*/master'
                ]],
                doGenerateSubmoduleConfigurations: false, 
                extensions: [],
                // [ $class: 'SparseCheckoutPaths', sparseCheckoutPaths: [[ path: '']]] 
                gitTool: 'Git_Centos', 
                submoduleCfg: [], 
                userRemoteConfigs: [[
                    credentialsId: 'GitHub_juliancho923', 
                    url: 'https://github.com/JULIANCHO923/Ceiba-Estacionamiento-julian.henao-'
                ]]
            ])
}

def descargarUltimaVersionAlphaAlJenkins(){

    def server = Artifactory.server 'Artifactory_Version_6.5.9'
    def downloadSpec = '''{
                            "files": [
                                {
                                	"pattern": "jenkins-snapshot/CoachEPM/Java/Parqueadero/alpha/${versionamiento}",
                  					"target": "artefactos/alpha/"                                                                                             
                                    "flat": "true",
                                    "recursive": "true"
                                }
                            ]
                        }'''
        server.download(downloadSpec)
}

def descargarVersionEstableReleaseAlJenkins(){

    def server = Artifactory.server 'Artifactory_Version_6.5.9'
    def downloadSpec = '''{
                            "files": [
                                {
                                	"pattern": "jenkins-snapshot/CoachEPM/Java/Parqueadero/release/estable/",                                    
                                    "target": "artefactos/release/",                      
                                    "flat": "true",
                                    "recursive": "true"
                                }
                            ]
                        }'''
        server.download(downloadSpec)
}

def publicarArtefacto(carpeta){
    def server = Artifactory.server 'Artifactory_Version_6.5.9'
    env.targetString = "${carpeta}"
    def uploadSpec = '''{
                            "files": [
                                    {
                                        "pattern": "build/libs/adnjulianhenao-(*).war",
                                        "target": "jenkins-snapshot/CoachEPM/Java/Parqueadero/${targetString}/${versionamiento}/adnjulianhenao.war"
                                    }
                            ]
                        }'''
    
    def buildInfo = Artifactory.newBuildInfo()
        buildInfo.env.capture = true
        buildInfo.number = "${carpeta}-${versionamiento}"
        server.upload spec: uploadSpec, buildInfo: buildInfo
        server.publishBuildInfo buildInfo    
}

def publicarArtefactoEstableRelease(){
    def server = Artifactory.server 'Artifactory_Version_6.5.9'
    def uploadSpec = '''{
                            "files": [
                                    {
                                        "pattern": "build/libs/adnjulianhenao-(*).war",
                                        "target": "jenkins-snapshot/CoachEPM/Java/Parqueadero/release/estable/adnjulianhenao.jar"
                                    }
                            ]
                        }'''
    
    def buildInfo = Artifactory.newBuildInfo()
        buildInfo.env.capture = true
        buildInfo.number = "release-estable-${versionamiento}"
        server.upload spec: uploadSpec, buildInfo: buildInfo
        server.publishBuildInfo buildInfo    
}

def eliminarCarpetaArtefactosEnJenkins(){
    dir("artefactos/"){
        deleteDir()
    }
}

def notificar(){
    if(currentBuild.result == 'FAILURE'){    	
        mail to: 'julian.henao@ceiba.com.co',
            subject: "El pipeline ha fallado: ${currentBuild.fullDisplayName}",
            body: "para verlo puede dar clic en  ${env.BUILD_URL}"        
    }    
}

def deploy(){

    //sshPublisher(publishers: [sshPublisherDesc(configName: 'FunctionalTest', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: 'echo Qwert08642 | sudo -S ls -al', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: './artefactos', remoteDirectorySDF: false, removePrefix: '', sourceFiles: 'artefactos/alpha/*.jar')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
    
    sshPublisher(
        publishers: [
            sshPublisherDesc(
                configName: 'FunctionalTest', 
                transfers: [
                    sshTransfer(
                        excludes: '', 
                        execCommand: '''
                            echo Qwert08642 | sudo -S ls -al
                        ''', 
                        execTimeout: 220000, 
                        flatten: false, 
                        makeEmptyDirs: false, 
                        noDefaultExcludes: false, 
                        patternSeparator: '', 
                        remoteDirectory: 'artefactos', 
                        remoteDirectorySDF: false, 
                        removePrefix: '', 
                        sourceFiles: './artefactos/alpha/'
                    )
                ], 
                usePromotionTimestamp: false, 
                useWorkspaceInPromotion: false, 
                verbose: false
            )
        ]
    )
}