package exceptions;

public class JSONException extends FileException{

    public JSONException(){}

    public JSONException(String s,String logFileName){
        super(s,logFileName);
    }
}
