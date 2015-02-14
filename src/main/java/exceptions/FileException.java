package exceptions;
import entities.LogBuilder;

public class FileException extends Exception{

    public FileException(){
    }

    public FileException(String s,String logFileName){
        super(s);
        LogBuilder logBuilder=new LogBuilder(logFileName);
        logBuilder.writeToLog(s);
        logBuilder.closeFile();
    }
}
