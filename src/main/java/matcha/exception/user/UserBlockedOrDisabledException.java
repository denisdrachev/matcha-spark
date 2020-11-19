package matcha.exception.user;


public class UserBlockedOrDisabledException extends RuntimeException {
    public UserBlockedOrDisabledException(String message) {
        super(message);
    }
}
