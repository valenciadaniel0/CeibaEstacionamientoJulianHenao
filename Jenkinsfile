pipeline {  
//Donde se va a ejecutar el Pipeline  
  agent {
    label 'Slave_Induccion'
  }
  
  //Opciones espec�ficas de Pipeline dentro del Pipeline  
  options {
    //Mantener artefactos y salida de consola para el # espec�fico de ejecucionesrecientes del Pipeline.
    buildDiscarder(logRotator(numToKeepStr: '3'))
    //No permitir ejecuciones concurrentes de Pipeline
      disableConcurrentBuilds()  
  }
  
    //Una secci�n que define las herramientas para �autoinstalar� y poner en la PATH  
  tools {    
    jdk 'JDK8_Centos' //Preinstalada en la Configuraci�n del Master    
    gradle 'Gradle4.5_Centos' //Preinstalada en la Configuraci�n del Master  
  }
  
    //Aqu� comienzan los �items� del Pipeline  
  stages{
    stage('Checkout') {
      steps{
        echo "------------>Checkout<------------"
	      checkout([$class: 'GitSCM', branches: [[name: '*/master']],
			doGenerateSubmoduleConfigurations: false, extensions: [], gitTool:
			'Git_Centos', submoduleCfg: [], userRemoteConfigs: [[credentialsId:'GitHub_juliancho923',url:'https://github.com/JULIANCHO923/Ceiba-Estacionamiento-julian.henao-']]])
      }
    }
    
    stage('Compile'){
            steps{
                echo "------------>Compile<------------"
                sh 'gradle clean'
		sh 'gradle --b ./build.gradle compileJava'		  
            }
        }    
    
    stage('Unit Tests') {      
      steps{        
        echo "------------>Unit Tests<------------"      
        sh 'gradle --b ./build.gradle test'
   //     junit '**/build/test-results/test/*.xml' //aggregate test results - JUnit
	
      }    
    }
    
    stage('Integration Tests') {      
      steps {
        echo "------------>Integration Tests<------------"  
        sh 'gradle --b ./build.gradle iTest'
     //    junit '**/build/test-results/iTest/*.xml' //aggregate test results - JUnit
	
      }    
    }
       


stage('Static Code Analysis') {
    steps
    {
      echo '------------>An�lisis de c�digo est�tico<------------'
      withSonarQubeEnv('Sonar') {
	   sh "${tool name: 'SonarScanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'}/bin/sonar-scanner"             
        }     
    }
  }
  
  
 stage('Build') {      
    steps {
      echo "------------>Build<------------"
      //Construir sin tarea test que se ejecut� previamente
      sh 'gradle --b ./build.gradle build -x test'      
    }    
  }  
  
  stage('Publish') {       
	        steps{
		        echo '------------>BEGIN Publish [Artifactory]<------------'
		        script{ //takes a block of Scripted Pipeline and executes that in the Declarative Pipeline
		            def server = Artifactory.server 'ar7if4c70ry@c318a'
		            def uploadSpec = '''
		            {"files": [{		          
		                "pattern": "build/libs/*.war",
		                "target": "libs-snapshot-local/$JOB_NAME/build/"
		                }]}'''
		
	                def buildInfo = server.upload(uploadSpec)
	                server.publishBuildInfo(buildInfo)
	                
	                
				echo '------------>END Publish [Artifactory]<------------'
		       }
            }
        }    
        
        stage("Deployment in testing environment") {
			steps {
				echo '------------>BEGIN Deployment<------------'
				sshPublisher(
					publishers: [
						sshPublisherDesc(
							configName: 'FunctionalTest', 
							transfers: [
								sshTransfer(excludes: '', 
								execCommand: ''' wget http://artifactory.ceiba.com.co/artifactory/libs-snapshot-local/CeibaInduccion/Ceiba-Estacionamiento(julian.henao)/build/adnjulianhenao-1.0-SNAPSHOT.war
								mv adnjulianhenao-1.0-SNAPSHOT.war pruebaDespliegue/parqueadero/backEnd/JulianHenao_adnjulianhenao-1.0-SNAPSHOT.war ''', 
								execTimeout: 120000, 
								flatten: false, 
								makeEmptyDirs: false, 
								noDefaultExcludes: false, 
								patternSeparator: '', 
								remoteDirectory: './pruebaDespliegue/parqueadero/backEnd', 
								remoteDirectorySDF: false, 
								removePrefix: '', 
								sourceFiles: 'adnjulianhenao-1.0-SNAPSHOT.war')
							], 
							usePromotionTimestamp: false, 
							useWorkspaceInPromotion: false, 
							verbose: false
						)
					]
				)
				echo '------------>END Deployment<------------'                
			}
		}
        
}
	
post {    
  always {      
    echo 'This will always run'  
     junit '**/build/test-results/test/*.xml'
     junit '**/build/test-results/iTest/*.xml'
     jacoco classPattern:'**/build/classes/java', sourcePattern:'**/src/main/java', execPattern:'**/build/jacoco/*.exec'
     
  }    
  success {      
    echo 'Esto correr� solo si se ejecuta satisfactoriamente'   
    // Se ejecutar� correctamente, siempre y cuando exista la ruta expuesta
   
  }    
  failure {   
   
    echo 'This will run only if failed' 
    //      Send notifications about a Pipeline to an email
    mail (to: 'julian.henao@ceiba.com.co',
               subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
               body: "Something is wrong with ${env.BUILD_URL}")
  }    
  unstable {      
    echo 'This will run only if the run was marked as unstable'    
  }    
  changed {      
    echo 'This will run only if the state of the Pipeline has changed'      
    echo 'For example, if the Pipeline was previously failing but is now successful'
    
    //      This will run only if the state of the Pipeline has changed
    //      For example, if the Pipeline was previously failing but is now successful'
    //      Send notifications about a Pipeline to an email
          mail (to: 'julian.henao@ceiba.com.co',
               subject: "Changed State Pipeline: ${currentBuild.fullDisplayName}",
               body: "The state of the Pipeline has changed. See ${env.BUILD_URL}")
  }  
 }   
 
 }
 