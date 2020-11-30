package matcha.exception.user;


import matcha.exception.BaseException;

public class UserAuthException extends BaseException {

    public UserAuthException() {
        super("Ошибка авторизации");
    }

    public UserAuthException(String message) {
        super(message);
    }
}
