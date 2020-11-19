package matcha.exception.user;


public class UserLoginOrPasswordIncorrectException extends RuntimeException {
    public UserLoginOrPasswordIncorrectException() {
        super("Некорректное имя пользователя или пароль");
    }
}
