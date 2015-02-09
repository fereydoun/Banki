package exceptions;

public class JSONException extends FileException{

    public JSONException(){}

    public JSONException(String s){
        super(s);
        System.out.println(s);
    }
}
