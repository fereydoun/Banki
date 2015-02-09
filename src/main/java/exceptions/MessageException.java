package exceptions;

public class MessageException extends Exception {

    public MessageException(){

    }

    public MessageException(String s){
        super(s);
        System.out.println(s);
    }
}
