package exceptions;

public class NoSuchUserExistsException extends RuntimeException {
    public NoSuchUserExistsException(String email) {
        super("User not found with email: " + email);
    }
}
