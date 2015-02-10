package exceptions;

public class UpperBoundException extends DepositException{

    public UpperBoundException(){

    }

    public UpperBoundException(String s,String logFileName){
        super(s,logFileName);
    }
}
