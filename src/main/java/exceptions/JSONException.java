package exceptions;

/**
 * Created by Dotin school 5 on 2/8/2015.
 */
public class JSONException extends FileException {

    public JSONException(){}

    public JSONException(String s)
    {
        super(s);

        System.out.println(s);
    }
}
