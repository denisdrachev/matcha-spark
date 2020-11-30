package matcha.exception.db;


import matcha.exception.BaseException;

public class SelectDBException extends BaseException {
    public SelectDBException() {
        super("Ошибка получения данных из базы");
    }

    public SelectDBException(String message) {
        super(message);
    }
}
