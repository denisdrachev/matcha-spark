package matcha.exception.user;


public class UserUpdateException extends RuntimeException {
    public UserUpdateException() {
        super("Ошибка сохранения профиля");
    }
}
