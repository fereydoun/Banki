package exceptions;

public class XMLException extends FileException{

    public XMLException(){

    }

    public XMLException(String s,String logFileName){
        super(s,logFileName);
    }
}
