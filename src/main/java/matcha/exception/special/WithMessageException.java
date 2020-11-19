package matcha.exception.special;


public class WithMessageException extends RuntimeException {
    public WithMessageException(String message) {
        super(message);
    }
}
