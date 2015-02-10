package exceptions;
import entities.LogBuilder;

public class DepositException extends Exception {

    public DepositException()
    {
        super();
    }

    public DepositException(String s,String logFileName)
    {
        super(s);
        LogBuilder logBuilder=new LogBuilder(logFileName);
        logBuilder.writeToLog(s);
    }
}
