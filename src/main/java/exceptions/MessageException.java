package exceptions;

/**
 * Created by Dotin school 5 on 2/8/2015.
 */
public class MessageException extends Exception {

    public MessageException()
    {

    }

    public MessageException(String s)
    {
        super(s);

        System.out.println(s);
    }
}
