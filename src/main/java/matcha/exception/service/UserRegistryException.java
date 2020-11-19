package matcha.exception.service;


public class UserRegistryException extends RuntimeException {
    public UserRegistryException() {
        super("Ошибка регистрации пользователя");
    }
}
