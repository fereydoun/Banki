package exceptions;

/**
 * Created by Dotin school 5 on 2/7/2015.
 */
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
