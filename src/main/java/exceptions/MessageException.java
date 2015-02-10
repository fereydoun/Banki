package exceptions;
import entities.LogBuilder;

public class MessageException extends Exception {

    public MessageException(){

    }

    public MessageException(String s,String logFileName){
        super(s);
        LogBuilder logBuilder=new LogBuilder(logFileName);
        logBuilder.writeToLog(s);
    }
}
