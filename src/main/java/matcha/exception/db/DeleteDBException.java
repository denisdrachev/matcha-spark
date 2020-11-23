package matcha.exception.db;


public class DeleteDBException extends RuntimeException {
    public DeleteDBException() {
        super("Ошибка удаления из базы");
    }
}
