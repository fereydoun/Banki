package exceptions;

public class XMLException extends FileException{

    public XMLException(){

    }

    public XMLException(String s){
        super(s);
        System.out.println(s);
    }
}
