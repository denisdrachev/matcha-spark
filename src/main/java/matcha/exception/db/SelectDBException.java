package matcha.exception.db;


public class SelectDBException extends RuntimeException {
    public SelectDBException() {
        super("Ошибка получения данных из базы");
    }
}
