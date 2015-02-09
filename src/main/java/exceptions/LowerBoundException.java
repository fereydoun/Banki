package exceptions;

public class LowerBoundException  extends DepositException {

    public LowerBoundException(){
    }

    public LowerBoundException(String s){

        super(s);
        System.out.println(s);
    }
}
