package exceptions;

public class EmailAlreadyExists extends RuntimeException{
    public EmailAlreadyExists(){
        super("Email is already registered");
    }
    public EmailAlreadyExists(String message){
        super (message);
    }
}
