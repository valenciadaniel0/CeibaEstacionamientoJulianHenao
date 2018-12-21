import java.text.SimpleDateFormat
//import hudson.model.Build;
 import jenkins.model.Jenkins
            import hudson.model.*
            
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
        env.etapa = ""
        env.errorEncontrado = ""
    
        // INICIO STAGES

       stage('GEt'){
            echo "####################->Init Get<-####################"        
     

        def test_job = Jenkins.instance.getItemByFullName("${JOB_NAME}")
        def last_sucessful_build_number=test_job.getLastSuccessfulBuild().getNumber()   
        print(last_sucessful_build_number)
        def last_versionamiento=test_job.getLastSuccessfulBuild().getEnvironment()
        print(last_versionamiento)
        echo "111############################"
        def last_versionamiento3=test_job.getLastSuccessfulBuild().getCharacteristicEnvVars()
        print(last_versionamiento3)
        echo "222############################"
        def last_versionamiento4=test_job.getLastSuccessfulBuild().getProperties()
        //print(last_versionamiento4)
        echo "333############################"
        print(last_versionamiento4.time)
        echo "444############################"
        
        //print(last_versionamiento4.time["DAY"])
        //print(last_versionamiento4.time["MONTH"])
        //print(last_versionamiento4.time["YEAR"])
        
        
        //print(last_versionamiento4.time["date"])
//        def format2 = Date.parse("dd MM yy hh:mm:ss T yyyy", last_versionamiento4.time).format("dd-MMMMM-yyyy")
  
  String versionamientoUltimoEstable = new SimpleDateFormat("dd-MMMMM-yyyy").format(last_versionamiento4.time)+"."+last_sucessful_build_number
  print(versionamientoUltimoEstable);
     
 echo "####################->End Get<-####################"        
        }

        stage('Checkout') {
        	echo "####################->Init Checkout<-####################"
        	env.etapa = "Checkout"
            checkout()
            echo "####################->End Checkout<-####################"
        }
        
        stage('Clean') {
        	echo "####################->Init Clean<-####################"
        	env.etapa = "Clean"
            bat './gradlew clean'
            echo "####################->End Clean<-####################"
        }
        
        stage('Compile') {
        	echo "####################->Init Compile<-####################"
        	env.etapa = "Compile"
            bat './gradlew compileJava'
            echo "####################->End Compile<-####################"
        }
		
		stage('Unit Test') {
        	echo "####################->Init Unit Test<-####################"
        	env.etapa = "Unit Test"
            bat './gradlew test'
            junit '**/build/test-results/test/*.xml'
            echo "####################->End Unit Test<-####################"
        }
      
        stage('Sonar'){
        	echo "####################->Init Sonar<-####################"
        	env.etapa = "Sonar"
            withSonarQubeEnv('Sonar') {
                bat "..\\..\\..\\tools\\hudson.plugins.sonar.SonarRunnerInstallation\\SonarScanner\\bin\\sonar-scanner -Dproject.settings=sonar-project.properties"
            }  
            echo "####################->End Sonar<-####################"
        }
        
        stage("Quality Gate"){
        	echo "####################->Init Quality Gate<-####################"
        	env.etapa = "Quality Gate"
        	sleep 5
            timeout(time: 15, unit: 'MINUTES') {
                def qg = waitForQualityGate()
                if (qg.status != 'OK') {
                    error "Pipeline abortado porque el quality gate del an�lisis del sonar no es OK: ${qg.status}"
                }
            }
            echo "####################->End Quality Gate<-####################"
        }

		stage('Integration Test') {
        	echo "####################->Init Integration Test<-####################"
            env.etapa = "Integration Test"
            bat './gradlew iTest'
            junit '**/build/test-results/iTest/*.xml'                                
            echo "####################->End Integration Test<-####################"
        }
		
        stage('Build') {
        	echo "####################->Init Build<-####################"
            env.etapa = "Build"
            bat './gradlew build -x test'
            echo "####################->End Build<-####################"
        }
     
        stage('Publish Alpha') {
	        echo "####################->Init Publish Alpha<-####################"
	        env.etapa = "Publish Alpha"
            publicarArtefacto("alpha")
            echo "####################->End Publish Alpha<-####################"        
        }
		
		stage("Download Alpha version to Jenkins"){
			echo "####################->Init Deploy Dllo<-####################"
			env.etapa = "Download Alpha version to Jenkins"
			descargarUltimaVersionAlJenkins("alpha")
			dir("artefactos/alpha/"){
                    bat 'dir'
            }
		}
		
        stage('Deploy Dllo') {
        	echo "####################->Init Deploy Dllo<-####################"                                    
            env.etapa = "Deploy Dllo"
            deploy()
            echo "####################->End Deploy Dllo<-####################"
        }

        stage('Testing into Dllo') {
            echo "####################->Init Testing into Dllo<-####################"
            env.etapa = "Testing into Dllo"    
            try{
                if(testDesarrollo==false){
                    error("Fallaron los test en el ambiente de desarrollo")
                }
            }catch(err) {
                println(err.getMessage());
                throw err
            }
            echo "####################->End Testing into Dllo<-####################"        
        }

        stage('Publish Beta') {
        	echo "####################->Init Publish Beta<-####################"
        	env.etapa = "Publish Beta"        
            publicarArtefacto("beta")
            echo "####################->End Publish Beta<-####################"        
        }

        stage('Deploy QA') {
        	echo "####################->Init Deploy into QA<-####################"        
            env.etapa = "Deploy QA" 
            echo "####################->End Deploy into QA<-####################"
        }

        stage('Testing into QA') { 
        	echo "####################->Init Testing into QA<-####################"
        	env.etapa = "Testing into QA"       
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
        	env.etapa = "Publish Release Candidate"        
            publicarArtefacto("release-candidate")
            echo "####################->End Publish Release Candidate<-####################"        
        }

        stage('Deploy Production') {    
            echo "####################->Init Deploy Production<-####################"
            env.etapa = "Deploy Production"
            // Desplegar en  Produccion
              
            echo "####################->End Deploy Production<-####################"
        }

        stage('Testing into Production') {
        	echo "####################->Init Testing into Production<-####################"
        	env.etapa = "Testing into Production"
            try{
                if(testProduccion==false){
                    error("fallaron los test en el ambiente de Producci�n")
                }
            }catch(err) {
                /// SE DEBE REALIZAR ROLLBACK EN CASO DE ERROR Y TRAER ULTIMO ESTABLE
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

		stage('Send Email for get approval of deploy in production '){
            sendEmailApprovalEmail('todas las pruebas funcionales pasaron, se requiere su aprobaci�n para hacer el despliegue en producci�n','por favor apruebe la tarea de despliegue en producci�n del sistema')
            input id: 'DeployProd', message: 'Aprobaci�n de paso a despliegue en producci�n', ok: 'OK', parameters: [choice(choices: ['Aprobar', 'Rechazar'], description: 'lista de opciones de aprobaci�n', name: 'Estados aprobaciones')], submitterParameter: 'isApprove'
           resultadoAprobacion = "'${env.isApprove}'"
         //   println('resultado variable de resultado aprobacion ${env.isApprove}')
            if(env.resultadoAprobacion == 'Rechazar'){
            echo '###------------------- La tarea de despliegue fue rechazada-----#######'
            }else{
                echo '######------- La tarea de despliegue fue aprobada----############'
            }
            
        }

        stage('Publish Release') {
        	echo "####################->Init Publish Release<-####################"
            env.etapa = "Publish Release"
            publicarArtefacto("release")
            env.etapa = "Publish Release/estable"
            publicarArtefacto("release/estable")
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
        env.errorEncontrado = err.getMessage()
        currentBuild.result = 'FAILURE'
    }finally{    	
        jacoco classPattern:'**/build/classes/java', sourcePattern:'**/src/main/java', execPattern:'**/build/jacoco/*.exec'
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

def descargarUltimaVersionAlJenkins(carpeta){
    def server = Artifactory.server 'ar7if4c70ry@c318a'
    env.targetString = "${carpeta}"
    def downloadSpec = '''{
                            "files": [
                                {
                                	"pattern": "jenkins-snapshot/CoachEPM/Java/Parqueadero/${targetString}/${versionamiento}",
                  					"target": "artefactos/${targetString}/",                                                                                             
                                    "flat": "true",
                                    "recursive": "true"
                                }
                            ]
                        }'''
        server.download(downloadSpec)
}

def publicarArtefacto(carpeta){
    def server = Artifactory.server 'ar7if4c70ry@c318a'
    env.targetString = "${carpeta}"
    def uploadSpec = '''{
                            "files": [
                                    {
                                        "pattern": "build/libs/adnjulianhenao.war",
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


def notificar(){
    if(currentBuild.result == 'FAILURE'){    	
        mail to: 'julian.henao@ceiba.com.co',
            subject: "El pipeline ha fallado en la etapa: #<- ${env.etapa} -># ",
            body: "Puede acceder directamente por el siguiente enlace:  ${env.BUILD_URL} \n ############# El error encontrado fue: ############# \n ${env.errorEncontrado}"
    }    
}

def sendEmailApprovalEmail(bodyEmail,bodyInput){
    mail (to: 'julian.henao@ceiba.com.co',
    subject: "Job '${env.JOB_NAME}' (${env.BUILD_NUMBER}):",
    body: "Por favor ir a la siguiente direcci�n ${env.BUILD_URL}/input/ ${bodyEmail}, y aprobar la tarea de des�liegue a producci�n para continuar con el pipeline. gracias");
    input "'${bodyInput}'";
}
