package matcha.exception.context;


import matcha.exception.BaseException;

public class UserRegistrationException extends BaseException {
    public UserRegistrationException() {
        super("Ошибка регистрации пользователя");
    }

    public UserRegistrationException(String message) {
        super(message);
    }
}
