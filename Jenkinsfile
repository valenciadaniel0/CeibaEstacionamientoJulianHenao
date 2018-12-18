import java.text.SimpleDateFormat

node('Slave_Induccion') {

    try{
        def dateFormat = new SimpleDateFormat("dd-MMMMM-yyyy")
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
        env.sonarHome= tool name: 'SonarScanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
        
        // variables de ambiente
        env.directorio = ""
        env.versionamiento = "${dateNow}.${BUILD_NUMBER}"
        env.targeString = ""
        
        // INICIO STAGES

        stage('Checkout') {
        	echo "####################->Init Checkout<-####################"
            checkout()
            echo "####################->End Checkout<-####################"
        }
        
        stage('Clean') {
        	echo "####################->Init Clean<-####################"                        
            dir("${env.directorio}"){
                bat './gradlew clean'
            }
            echo "####################->End Clean<-####################"
        }
        
        stage('Compile') {
        	echo "####################->Init Compile<-####################"
            dir("${env.directorio}"){
                bat './gradlew compileJava'
            }
            echo "####################->End Compile<-####################"
        }

        stage('Unit Test') {
        	echo "####################->Init Unit Test<-####################"
            dir("${env.directorio}"){
                bat './gradlew test'
                junit '**/build/test-results/test/*.xml'                
            }
            echo "####################->End Unit Test<-####################"
        }
      
        stage('Sonar'){
        	echo "####################->Init Sonar<-####################"
            withSonarQubeEnv('Sonar') {
                bat "${sonarHome}/bin/sonar-scanner -Dproject.settings=./${env.directorio}/sonar-project.properties"
            }  
            echo "####################->End Sonar<-####################"
        }
        
        stage("Quality Gate"){
        	echo "####################->Init Quality Gate<-####################"
            timeout(time: 1, unit: 'HOURS') {
                def qg = waitForQualityGate()
                if (qg.status != 'OK') {
                    error "Pipeline abortado porque el quality gate del análisis del sonar no es OK: ${qg.status}"
                }
            }
            echo "####################->End Quality Gate<-####################"
        }


		stage('Integration Test') {
        	echo "####################->Init Integration Test<-####################"
            dir("${env.directorio}"){
                bat './gradlew iTest'
                junit '**/build/test-results/iTest/*.xml'                                
            }
            echo "####################->End Integration Test<-####################"
        }
		
        stage('Build') {
        	echo "####################->Init Build<-####################"
            dir("${env.directorio}"){
                bat './gradlew build -x test'
            }
            echo "####################->End Build<-####################"
        }
        
        stage('Publish Alpha') {
	        echo "####################->Init Publish Alpha<-####################"        
            publicarArtefacto("alpha")
            echo "####################->End Publish Alpha<-####################"        
        }
		
		stage("Download Alpha version to Jenkins"){
			echo "####################->Init Deploy Dllo<-####################"
			descargarUltimaVersionAlJenkins("alpha")
			dir("artefactos/alpha/"){
                    bat 'dir'
            }
		}


		
        stage('Deploy Dllo') {
        	echo "####################->Init Deploy Dllo<-####################"                                    
            //desplegar en dllo
            deploy()
            echo "####################->End Deploy Dllo<-####################"
        }

        stage('Testing into Dllo') {
            echo "####################->Init Testing into Dllo<-####################"    
            try{
                if(testDesarrollo==false){
                    error("fallaron los test en el ambiente de desarrollo")
                }
            }catch(err) {
                println(err.getMessage());
                throw err
            }
            echo "####################->End Testing into Dllo<-####################"        
        }

        stage('Publish Beta') {
        	echo "####################->Init Publish Beta<-####################"        
            publicarArtefacto("beta")
            echo "####################->End Publish Beta<-####################"        
        }

        stage('Deploy QA') {
        	echo "####################->Init Deploy into QA<-####################"        
            // Desplegar en  QA  
            echo "####################->End Deploy into QA<-####################"
        }

        stage('Testing into QA') { 
        	echo "####################->Init Testing into QA<-####################"       
            try{
                if(testQA==false){
                    error("fallaron los test en el ambiente de QA")
                }
            }catch(err) {
                println(err.getMessage());
                throw err
            }
            echo "####################->End Testing into QA<-####################"        
        }

        stage('Publish Release Candidate') {
        	echo "####################->Init Publish Release Candidate<-####################"        
            publicarArtefacto("release-candidate")
            echo "####################->End Publish Release Candidate<-####################"        
        }

        stage('Deploy Production') {    
            echo "####################->Init Deploy Production<-####################"
            // Desplegar en  Produccion  
            echo "####################->End Deploy Production<-####################"
        }

        stage('Testing into Production') {
        	echo "####################->Init Testing into Production<-####################"
            try{
                if(testProduccion==false){
                    error("fallaron los test en el ambiente de Producción")
                }
            }catch(err) {
                descargarUltimaVersionAlJenkins("release/estable")
                println(err.getMessage());
                dir("artefactos/release/"){
                    echo "estos son los archivos en artefactos/release en jenkins"
                    bat 'dir'
                }
                // enviar notificacion warning
                throw err
            }
            echo "####################->End Testing into Production<-####################"        
        }

        stage('Publish Release') {        
        	echo "####################->Init Publish Release<-####################"
            publicarArtefacto("release")
            publicarArtefactoEstableRelease()
            echo "####################->End Publish Release<-####################"       
        }
        
        /*
        stage('Clean Workspace') {
        	echo "####################->Init Clean Workspace<-####################"        
            eliminarCarpetaArtefactosEnJenkins()
            echo "####################->End Clean Workspace<-####################"
        }
        */
        // fin stages

    }catch(err){
        echo "Hubo un error en el pipeline"

    }finally{
    	accionesPost()
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

def descargarUltimaVersionAlJenkins(carpeta){
	
    def server = Artifactory.server 'Artifactory_Version_6.5.9'
    
    env.targetString = "${carpeta}"
    
    def downloadSpec = '''{
                            "files": [
                                {
                                	"pattern": "jenkins-snapshot/CoachEPM/Java/Parqueadero/${targetString}/${versionamiento}",
                  					"target": "artefactos/${targetString}/"                                                                                             
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
                        	echo Qwert08642 | sudo -S systemctl stop servicioADNCeiba.service
                        	pwd                        
                            ls -al
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

def accionesPost(){
    post{
        always{
            jacoco classPattern:'**/build/classes/java', sourcePattern:'**/src/main/java', execPattern:'**/build/jacoco/*.exec'
        }
        failure{
                notificar()       
    	}       
    }
}

def notificar(){
    if(currentBuild.result == 'FAILURE'){    	
        mail to: 'julian.henao@ceiba.com.co',
            subject: "El pipeline ha fallado: ${currentBuild.fullDisplayName}",
            body: "para verlo puede dar clic en  ${env.BUILD_URL}"        
    }    
}
