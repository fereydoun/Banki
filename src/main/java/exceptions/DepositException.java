package exceptions;

public class DepositException extends Exception {

    public DepositException()
    {
        super();
    }

    public DepositException(String s)
    {
        super(s);

        System.out.println(s);
    }
}
