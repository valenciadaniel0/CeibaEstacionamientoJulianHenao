pipeline {  
//Donde se va a ejecutar el Pipeline  
	agent {
    label 'Slave_Induccion'
  }
  
  //Opciones específicas de Pipeline dentro del Pipeline  
  options {
    //Mantener artefactos y salida de consola para el # específico de ejecucionesrecientes del Pipeline.
    buildDiscarder(logRotator(numToKeepStr: '3'))
    //No permitir ejecuciones concurrentes de Pipeline
      disableConcurrentBuilds()  
  }
  
    //Una sección que define las herramientas para “autoinstalar” y poner en la PATH  
  tools {    
    jdk 'JDK8_Centos' //Preinstalada en la Configuración del Master    
    gradle 'Gradle4.5_Centos' //Preinstalada en la Configuración del Master  
  }
  
    //Aquí comienzan los “items” del Pipeline  
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
    
   // stage('Unit Tests') {      
   //   steps{        
   //     echo "------------>Unit Tests<------------"      
   //     sh 'gradle --b ./build.gradle test'
   //     junit '**/build/test-results/test/*.xml' //aggregate test results - JUnit	
   //   }    
   // }
    
   // stage('Integration Tests') {      
  //    steps {
   //     echo "------------>Integration Tests<------------"  
    //    sh 'gradle --b ./build.gradle iTest'
     //    junit '**/build/test-results/iTest/*.xml' //aggregate test results - JUnit
	//
    //  }    
   // }
      


//stage('Static Code Analysis') {
//    steps
//    {
//      echo '------------>Análisis de código estático<------------'
//      withSonarQubeEnv('Sonar') {
//	   sh "${tool name: 'SonarScanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'}/bin/sonar-scanner"             
//        }     
//    }
//  }
  
  
 stage('Build') {      
    steps {
      echo "------------>Build<------------"
      //Construir sin tarea test que se ejecutó previamente
      sh 'gradle --b ./build.gradle build -x test'      
    }    
  }  
  
  stage('Publish ALFA') {       
	        steps{
		        echo '------------>BEGIN ALFA Publish [Artifactory]<------------'
		        script{ //takes a block of Scripted Pipeline and executes that in the Declarative Pipeline
		            def server = Artifactory.server 'ar7if4c70ry@c318a'
		            def uploadSpec = '''
		            {"files": [{		          
		                "pattern": "build/libs/*.war",
		                "target": "libs-snapshot-local/Parqueadero_Julian_Henao/ALFA/"
		                }]}'''
		            def buildInfo = server.upload(uploadSpec)
	                server.publishBuildInfo(buildInfo)	             	                
				echo '------------>END ALF Publish [Artifactory]<------------'
		       }
            }
        }    
        
        stage("Deployment BETA environment") {
			steps {
				echo '------------>BEGIN BETA Deployment<------------'
				sshPublisher(
					publishers: [
						sshPublisherDesc(
							configName: 'FunctionalTest', 
							transfers: [
								sshTransfer(excludes: '', 
								execCommand: '''echo Qwert08642 | sudo -S systemctl stop servicioADNCeiba.service 
								echo Qwert08642 | sudo -S rm adnjulianhenao.war
								wget http://artifactory.ceiba.com.co/artifactory/libs-snapshot-local/Parqueadero_Julian_Henao/ALFA/adnjulianhenao.war
								echo Qwert08642 | sudo -S mv adnjulianhenao.war CoachEPM/Java/versionamiento/beta/adnjulianhenao.war 
								echo Qwert08642 | sudo -S systemctl start servicioADNCeibaBeta.service ''', 
								execTimeout: 220000, 
								flatten: false, 
								makeEmptyDirs: false, 
								noDefaultExcludes: false, 
								patternSeparator: '', 
								remoteDirectory: './CoachEPM/Java/versionamiento/beta', 
								remoteDirectorySDF: false, 
								removePrefix: '', 
								sourceFiles: 'adnjulianhenao.war')
							], 
							usePromotionTimestamp: false, 
							useWorkspaceInPromotion: false, 
							verbose: false
						)
					]
				)
				echo '########>END BETA Deployment<------------'                
			}
		}
		
		
	stage('Functional Beta Tests') {      
      steps {
        echo "------------>FUNCTIONAL BETA Tests<------------"  
        sh 'gradle --b ./build.gradle fBetaTest'
     //    junit '**/build/test-results/iTest/*.xml' //aggregate test results - JUnit
	
      }    
      
      post{
          always{
              
              sshPublisher(
					publishers: [
						sshPublisherDesc(
							configName: 'FunctionalTest', 
							transfers: [
								sshTransfer(excludes: '', 
								execCommand: 'echo Qwert08642 | sudo -S systemctl stop servicioADNCeibaBeta.service ', 
								execTimeout: 220000, 
								flatten: false, 
								makeEmptyDirs: false, 
								noDefaultExcludes: false, 
								patternSeparator: '', 
								remoteDirectory: '', 
								remoteDirectorySDF: false, 
								removePrefix: '', 
								sourceFiles: '')
							], 
							usePromotionTimestamp: false, 
							useWorkspaceInPromotion: false, 
							verbose: false
						)
					]
				)
          }        
      }

    }
		
		
		  stage('Publish Beta') {       
	        steps{
		        echo '------------>BEGIN BETA Publish [Artifactory]<------------'
		        script{ //takes a block of Scripted Pipeline and executes that in the Declarative Pipeline
		            def server = Artifactory.server 'ar7if4c70ry@c318a'
		            def uploadSpec = '''
		            {"files": [{		          
		                "pattern": "build/libs/*.war",
		                "target": "libs-snapshot-local/Parqueadero_Julian_Henao/BETA/"
		                }]}'''
	                def buildInfo = server.upload(uploadSpec)
	                server.publishBuildInfo(buildInfo)
	                
	                
				echo '------------>END BETA Publish [Artifactory]<------------'
		       }
            }
        }  
	
	///// Release Candidate
	stage("Deployment RCandidate environment") {
			steps {
				echo '------------>BEGIN RCandidate Deployment<------------'
				sshPublisher(
					publishers: [
						sshPublisherDesc(
							configName: 'FunctionalTest', 
							transfers: [
								sshTransfer(excludes: '', 
								execCommand: '''wget http://artifactory.ceiba.com.co/artifactory/libs-snapshot-local/Parqueadero_Julian_Henao/BETA/adnjulianhenao.war
								echo Qwert08642 | sudo -S mv adnjulianhenao.war CoachEPM/Java/versionamiento/rc/adnjulianhenao.war 
								echo Qwert08642 | sudo -S systemctl start servicioADNCeibaRC.service ''', 
								execTimeout: 220000, 
								flatten: false, 
								makeEmptyDirs: false, 
								noDefaultExcludes: false, 
								patternSeparator: '', 
								remoteDirectory: './CoachEPM/Java/versionamiento/rc', 
								remoteDirectorySDF: false, 
								removePrefix: '', 
								sourceFiles: 'adnjulianhenao.war')
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
		
		
	stage('Functional RC Tests') {      
      steps {
        echo "------------>Functional RELEASE CANDIDATE Tests<------------"  
        sh 'gradle --b ./build.gradle fRCTest'
     //    junit '**/build/test-results/iTest/*.xml' //aggregate test results - JUnit
	
      }
        post{
          always{
              
              sshPublisher(
					publishers: [
						sshPublisherDesc(
							configName: 'FunctionalTest', 
							transfers: [
								sshTransfer(excludes: '', 
								execCommand: '	echo Qwert08642 | sudo -S systemctl stop servicioADNCeibaRC.service ', 
								execTimeout: 220000, 
								flatten: false, 
								makeEmptyDirs: false, 
								noDefaultExcludes: false, 
								patternSeparator: '', 
								remoteDirectory: '', 
								remoteDirectorySDF: false, 
								removePrefix: '', 
								sourceFiles: '')
							], 
							usePromotionTimestamp: false, 
							useWorkspaceInPromotion: false, 
							verbose: false
						)
					]
				)
          }        
      }    
    }
		
		
		  stage('Publish RCandidate') {       
	        steps{
		        echo '------------>BEGIN  R CANDIDATE Publish [Artifactory]<------------'
		        script{ //takes a block of Scripted Pipeline and executes that in the Declarative Pipeline
		            def server = Artifactory.server 'ar7if4c70ry@c318a'
		            def uploadSpec = '''
		            {"files": [{		          
		                "pattern": "build/libs/*.war",
		                "target": "libs-snapshot-local/Parqueadero_Julian_Henao/Release_Candidate/"
		                }]}'''
		            def buildInfo = server.upload(uploadSpec)
	                server.publishBuildInfo(buildInfo)	                	         
				echo '------------>END Publish [Artifactory]<------------'
		       }
            }
        }  
			
    
    /// RELEASE
    ///// Release Candidate
	stage("Deployment Release environment") {
			steps {
				echo '------------>BEGIN RELEASE Deployment<------------'
				sshPublisher(
					publishers: [
						sshPublisherDesc(
							configName: 'FunctionalTest', 
							transfers: [
								sshTransfer(excludes: '', 
								execCommand: ''' echo Qwert08642 | sudo -S mv CoachEPM/Java/versionamiento/adnjulianhenao.war CoachEPM/Java/versionamiento/ultimoEstable/adnjulianhenao.war
								wget http://artifactory.ceiba.com.co/artifactory/libs-snapshot-local/Parqueadero_Julian_Henao/Release_Candidate/adnjulianhenao.war
								echo Qwert08642 | sudo -S mv adnjulianhenao.war CoachEPM/Java/versionamiento/adnjulianhenao.war 
								echo Qwert08642 | sudo -S systemctl start servicioADNCeiba.service ''', 
								execTimeout: 220000, 
								flatten: false, 
								makeEmptyDirs: false, 
								noDefaultExcludes: false, 
								patternSeparator: '', 
								remoteDirectory: './CoachEPM/Java/versionamiento', 
								remoteDirectorySDF: false, 
								removePrefix: '', 
								sourceFiles: 'adnjulianhenao.war')
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
		
		
	stage('Functional RELEASE Tests') {      
      steps {
        echo "------------>FUNCTIONAL RELEASE Tests<------------"  
        sh 'gradle --b ./build.gradle fReleaseTest'
     //    junit '**/build/test-results/iTest/*.xml' //aggregate test results - JUnit
      }    
     post {        
  	 	failure {   
   
    echo 'This will run only if failed' 
    
   	echo '------------>ROLLBACK AMBIENTE PRODUCCION<------------'
				sshPublisher(
					publishers: [
						sshPublisherDesc(
							configName: 'FunctionalTest', 
							transfers: [
								sshTransfer(excludes: '', 
								execCommand: ''' echo Qwert08642 | sudo -S systemctl stop servicioADNCeiba.service								
								echo Qwert08642 | sudo -S mv CoachEPM/Java/versionamiento/ultimoEstable/adnjulianhenao.war CoachEPM/Java/versionamiento/adnjulianhenao.war					
								echo Qwert08642 | sudo -S systemctl start servicioADNCeiba.service ''', 
								execTimeout: 220000, 
								flatten: false, 
								makeEmptyDirs: false, 
								noDefaultExcludes: false, 
								patternSeparator: '', 
								remoteDirectory: '', 
								remoteDirectorySDF: false, 
								removePrefix: '', 
								sourceFiles: '')
							], 
							usePromotionTimestamp: false, 
							useWorkspaceInPromotion: false, 
							verbose: false
						)
					]
				)
				echo '------------>FIN ROLLBACK AMBIENTE PRODUCCION<------------'
   
    //      Send notifications about a Pipeline to an email
    mail (to: 'julian.henao@ceiba.com.co',
               subject: "FALLO EN PRODUCCION Failed Pipeline: ${currentBuild.fullDisplayName}",
               body: "Something is wrong with ${env.BUILD_URL}")
  			} 
  		}
    }
		
		
		  stage('Publish RELEASE') {       
	        steps{
		        echo '------------>BEGIN Publish [Artifactory]<------------'
		        script{ //takes a block of Scripted Pipeline and executes that in the Declarative Pipeline
		            def server = Artifactory.server 'ar7if4c70ry@c318a'
		            def uploadSpec = '''
		            {"files": [{		          
		                "pattern": "build/libs/*.war",
		                "target": "libs-snapshot-local/Parqueadero_Julian_Henao/Release/"
		                }]}'''
	                def buildInfo = server.upload(uploadSpec)
	                server.publishBuildInfo(buildInfo)	             
	                
				echo '------------>END Publish [Artifactory]<------------'
		       }
            }
        }  
	
    
        
}
	
post {    
  always {      
    echo 'This will always run'  
     //junit '**/build/test-results/test/*.xml'
     //junit '**/build/test-results/iTest/*.xml'
     //jacoco classPattern:'**/build/classes/java', sourcePattern:'**/src/main/java', execPattern:'**/build/jacoco/*.exec'
     
  }    
  success {      
    echo 'Esto correrá solo si se ejecuta satisfactoriamente'   
    // Se ejecutará correctamente, siempre y cuando exista la ruta expuesta
   
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
 