package exceptions;

public class BadUserCredentialException extends RuntimeException{
    public BadUserCredentialException() {
        super("Invalid username or password. Please try again.");
    }

    public BadUserCredentialException(String message) {
        super(message);
    }

}
