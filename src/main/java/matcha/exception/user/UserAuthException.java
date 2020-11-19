package matcha.exception.user;


public class UserAuthException extends RuntimeException {

    public UserAuthException() {
        super("Ошибка авторизации");
    }
}
