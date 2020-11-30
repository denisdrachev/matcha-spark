package matcha.exception.db;


import matcha.exception.BaseException;

public class UpdateDBException extends BaseException {
    public UpdateDBException() {
        super("Ошибка обновления данных базы");
    }

    public UpdateDBException(String message) {
        super(message);
    }
}
