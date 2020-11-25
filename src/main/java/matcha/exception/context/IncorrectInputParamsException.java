package matcha.exception.context;


public class IncorrectInputParamsException extends RuntimeException {
    public IncorrectInputParamsException() {
        super("Некорректные параметры запроса.");
    }
}
