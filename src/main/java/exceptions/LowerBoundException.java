package exceptions;

/**
 * Created by Dotin school 5 on 2/7/2015.
 */
public class LowerBoundException  extends DepositException {

    public LowerBoundException()
    {

    }

    public LowerBoundException(String s)
    {
        super(s);

        System.out.println(s);
    }
}
