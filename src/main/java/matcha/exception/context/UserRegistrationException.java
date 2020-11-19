package matcha.exception.context;


public class UserRegistrationException extends RuntimeException {
    public UserRegistrationException() {
        super("Ошибка регистрации пользователя");
    }
}
