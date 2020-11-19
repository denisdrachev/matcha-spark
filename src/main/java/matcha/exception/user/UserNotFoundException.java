package matcha.exception.user;


public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("Пользователь не найден");
    }

    public UserNotFoundException(String login) {
        super("Пользователь " + login + " не найден");
    }
}
