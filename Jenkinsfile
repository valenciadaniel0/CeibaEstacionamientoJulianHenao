pipeline {  
	  
	agent { //Donde se va a ejecutar el Pipeline
    	label 'Slave_Induccion'
  	}

	options { //Opciones especificas de Pipeline dentro del Pipeline    	
    	buildDiscarder(logRotator(numToKeepStr: '3')) //Mantener artefactos y salida de consola para el # especifico de ejecuciones recientes del Pipeline.    
      	disableConcurrentBuilds()  //No permitir ejecuciones concurrentes de Pipeline
	}
  
	tools { //Una seccion que define las herramientas para autoinstalar y poner en la PATH    
    	jdk 'JDK8_Centos' //Preinstalada en la Configuracion del Master    
    	gradle 'Gradle4.5_Centos' //Preinstalada en la Configuracion del Master  
	}  

	environment{      
    	nombreProyecto =  "adnjulianhenao_${BUILD_TIMESTAMP}_${BUILD_DISPLAY_NAME}.war"
  	}
  
	stages{	//Aqui comienzan los items del Pipeline 
    	
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
        		junit '**/build/test-results/test/*.xml' //aggregate test results - JUnit	
      		}    
    	}
    
    	stage('Integration Tests') {      
      		steps {
        		echo "------------>Integration Tests<------------"  
        		sh 'gradle --b ./build.gradle iTest'
         		junit '**/build/test-results/iTest/*.xml' //aggregate test results - JUnit
	  		}    
   		}
      
		stage('Static Code Analysis') {
    		steps{
      			echo '------------>Anilisis de codigo estatico<------------'
      			withSonarQubeEnv('Sonar') {
	   				sh "${tool name: 'SonarScanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'}/bin/sonar-scanner"             
        		}     
    		}
  		}
    
 		stage('Build') {      
    		steps {
      			echo "------------>Build<------------"      			
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
		                "target": "libs-snapshot-local/Parqueadero_Julian_Henao/ALFA/${env.nombreProyecto}"
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
								echo Qwert08642 | sudo -S rm ${env.nombreProyecto}
								wget http://artifactory.ceiba.com.co/artifactory/libs-snapshot-local/Parqueadero_Julian_Henao/ALFA/${env.nombreProyecto}
								echo Qwert08642 | sudo -S cp ${env.nombreProyecto} CoachEPM/Java/versionamiento/beta/adnjulianhenao.war 
								echo Qwert08642 | sudo -S mv ${env.nombreProyecto} CoachEPM/Java/versionamiento/beta/${env.nombreProyecto}
								echo Qwert08642 | sudo -S systemctl start servicioADNCeibaBeta.service ''', 
								execTimeout: 220000, 
								flatten: false, 
								makeEmptyDirs: false, 
								noDefaultExcludes: false, 
								patternSeparator: '', 
								remoteDirectory: './CoachEPM/Java/versionamiento/beta', 
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
				echo '########>END BETA Deployment<------------'                
			}
		}
		
		
		stage('Functional Beta Tests') {      
      		steps {
        		echo "------------>FUNCTIONAL BETA Tests<------------"  
        		sh 'gradle --b ./build.gradle fBetaTest'
     			 junit '**/build/test-results/fBetaTest/*.xml' //aggregate test results - JUnit	
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
		                "target": "libs-snapshot-local/Parqueadero_Julian_Henao/BETA/${env.nombreProyecto}"
		                }]}'''
	                def buildInfo = server.upload(uploadSpec)
	                server.publishBuildInfo(buildInfo)	                
					echo '------------>END BETA Publish [Artifactory]<------------'
		       }
			}
        }
		
		stage("Deployment RCandidate environment") { ///// Release Candidate
			steps {
				echo '------------>BEGIN RCandidate Deployment<------------'
				sshPublisher(
					publishers: [
						sshPublisherDesc(
							configName: 'FunctionalTest', 
							transfers: [
								sshTransfer(excludes: '', 
								execCommand: '''wget http://artifactory.ceiba.com.co/artifactory/libs-snapshot-local/Parqueadero_Julian_Henao/BETA/${env.nombreProyecto}
								echo Qwert08642 | sudo -S cp ${env.nombreProyecto} CoachEPM/Java/versionamiento/rc/adnjulianhenao.war
								echo Qwert08642 | sudo -S mv ${env.nombreProyecto} CoachEPM/Java/versionamiento/rc/${env.nombreProyecto} 
								echo Qwert08642 | sudo -S systemctl start servicioADNCeibaRC.service ''', 
								execTimeout: 220000, 
								flatten: false, 
								makeEmptyDirs: false, 
								noDefaultExcludes: false, 
								patternSeparator: '', 
								remoteDirectory: './CoachEPM/Java/versionamiento/rc', 
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
				echo '------------>END Deployment<------------'                
			}
		}		
		
		stage('Functional RCandidate Tests') {      
      		steps {
        		echo "------------>Functional RELEASE CANDIDATE Tests<------------"  
        		sh 'gradle --b ./build.gradle fRCTest'
     			junit '**/build/test-results/fRCTest/*.xml' //aggregate test results - JUnit	
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
		                "target": "libs-snapshot-local/Parqueadero_Julian_Henao/Release_Candidate/${env.nombreProyecto}"
		                }]}'''
		            def buildInfo = server.upload(uploadSpec)
	                server.publishBuildInfo(buildInfo)	                	         
				echo '------------>END Publish [Artifactory]<------------'
		       }
            }
        }  			
           
		stage("Deployment Release environment") { /// RELEASE
			steps {
				echo '------------>BEGIN RELEASE Deployment<------------'
				sshPublisher(
					publishers: [
						sshPublisherDesc(
							configName: 'FunctionalTest', 
							transfers: [
								sshTransfer(excludes: '', 
								execCommand: ''' echo Qwert08642 | sudo -S mv CoachEPM/Java/versionamiento/adnjulianhenao.war CoachEPM/Java/versionamiento/ultimoEstable/adnjulianhenao.war
								wget http://artifactory.ceiba.com.co/artifactory/libs-snapshot-local/Parqueadero_Julian_Henao/Release_Candidate/${env.nombreProyecto}
								echo Qwert08642 | sudo -S cp ${env.nombreProyecto} CoachEPM/Java/versionamiento/adnjulianhenao.war
								echo Qwert08642 | sudo -S mv ${env.nombreProyecto} CoachEPM/Java/versionamiento/${env.nombreProyecto} 
								echo Qwert08642 | sudo -S systemctl start servicioADNCeiba.service ''', 
								execTimeout: 220000, 
								flatten: false, 
								makeEmptyDirs: false, 
								noDefaultExcludes: false, 
								patternSeparator: '', 
								remoteDirectory: './CoachEPM/Java/versionamiento', 
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
				echo '------------>END Deployment<------------'                
			}
		}
				
		stage('Functional_RELEASE_Tests') {      
        	steps{
            	script {  //takes a block of Scripted Pipeline and executes that in the Declarative Pipeline                
          			try {
						echo "------------>FUNCTIONAL RELEASE Tests<------------"  
        				sh 'gradle --b ./build.gradle fReleaseTest'
        				junit '**/build/test-results/fReleaseTest/*.xml' //aggregate test results - JUnit
        				
        				echo '------------>BEGIN Publish [Artifactory]<------------'		        
						def server = Artifactory.server 'ar7if4c70ry@c318a'
		            	def uploadSpec = '''
		            		{"files": [{		          
		                	"pattern": "build/libs/*.war",
		                	"target": "libs-snapshot-local/Parqueadero_Julian_Henao/Release/${env.nombreProyecto}"
		                	}]}'''
	                	def buildInfo = server.upload(uploadSpec)
	                	server.publishBuildInfo(buildInfo)	             	                
						echo '------------>END Publish [Artifactory]<------------'  
						      
     				}catch (exc) {               
     				                             
                    	echo '###########>ROLLBACK WHENNN AMBIENTE PRODUCCION<############'                    
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
						echo '-############>FIN WHENN ROLLBACK AMBIENTE PRODUCCION<------------'
				    }
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
    		echo 'Esto correra solo si se ejecuta satisfactoriamente'       		   
  		}    
  		failure {      
    		echo 'This will run only if failed'     	       
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
          	mail (to: 'julian.henao@ceiba.com.co',
            	subject: "Changed State Pipeline: ${currentBuild.fullDisplayName}",
               	body: "The state of the Pipeline has changed. See ${env.BUILD_URL}")
  		}  
 	}
 }   