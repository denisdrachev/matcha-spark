package matcha.exception.user;


import matcha.exception.BaseException;

public class ProfileNotFilledException extends BaseException {

    public ProfileNotFilledException() {
        super("Профиль пользователя не заполнен");
    }

    public ProfileNotFilledException(String message) {
        super(message);
    }
}
