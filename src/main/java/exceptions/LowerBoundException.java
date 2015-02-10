package exceptions;

public class LowerBoundException  extends DepositException {

    public LowerBoundException(){
    }

    public LowerBoundException(String s,String logFileName){

        super(s,logFileName);
    }
}
