package exceptions;

public class UpperBoundException extends DepositException{

    public UpperBoundException(){

    }

    public UpperBoundException(String s){
        super(s);
        System.out.println(s);
    }
}
