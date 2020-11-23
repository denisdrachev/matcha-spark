package matcha.exception.db;


public class UpdateDBException extends RuntimeException {
    public UpdateDBException() {
        super("Ошибка обновления данных базы");
    }
}
