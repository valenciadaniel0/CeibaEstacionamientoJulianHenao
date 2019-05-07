def ejecutarTest(){
    def BatchFile = 'despliegues/test.bat';
    println ('ADENTRO DE TEST');    

    try{
        println ("cmd.exe /c test.bat".execute()).toString()
        Runtime.runtime.exec(BatchFile);
    }catch(e){
        println e
        println  "cmd.exe /c despliegues/test.bat".execute()
    }
}
