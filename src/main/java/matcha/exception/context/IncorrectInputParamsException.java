package matcha.exception.context;


import matcha.exception.BaseException;

public class IncorrectInputParamsException extends BaseException {
    public IncorrectInputParamsException() {
        super("Некорректные параметры запроса.");
    }

    public IncorrectInputParamsException(String message) {
        super(message);
    }
}
